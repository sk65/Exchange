package com.yefimoyevhen.exchange.interactors

import com.yefimoyevhen.exchange.api.toExchangeListDTO
import com.yefimoyevhen.exchange.database.toExchangeCashEntity
import com.yefimoyevhen.exchange.database.toExchangeListDTO
import com.yefimoyevhen.exchange.datasourse.LocalDataSource
import com.yefimoyevhen.exchange.datasourse.RemoteDataSource
import com.yefimoyevhen.exchange.model.ExchangeListDTO
import com.yefimoyevhen.exchange.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchAllExchangesUseCase
@Inject
constructor(
    private val internetChecker: InternetChecker,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val sharedPreferencesManager: SharedPreferencesManager
) {
    suspend fun fetchData(): Flow<DataState<List<ExchangeListDTO>>> = flow {
        emit(DataState.Loading)
        val requestTimestamp = sharedPreferencesManager.getRequestTimestamp()
        try {
            if (internetChecker.hasInternetConnection()) {
                if (internetChecker.hasInternetConnection() &&
                    (requestTimestamp == SharedPreferencesManager.DEF_TIMESTAMP_VALUE
                            || isRefreshTimeout(requestTimestamp))
                ) {
                    val networkEntries = remoteDataSource.getAllNetworkEntries(getCurrentDate())
                    val exchangeTimestamp = networkEntries.timestamp
                    sharedPreferencesManager.refreshExchangeTimestamp(exchangeTimestamp)
                    sharedPreferencesManager.refreshRequestTimestamp(System.currentTimeMillis())
                    val entities = networkEntries.toExchangeListDTO()
                    emit(DataState.Success(entities))

                    localDataSource.apply {
                        deleteAllEntries()
                        insertEntries(entities.toExchangeCashEntity())
                    }
                } else {
                    val findAllEntries =
                        localDataSource.findAllEntries(sharedPreferencesManager.getExchangeTimestamp())
                    emit(DataState.Success(findAllEntries.toExchangeListDTO()))
                }
            }
        } catch (e: Exception) {
            emit(DataState.Error(e.localizedMessage ?: "Something goes wrong"))
        }
    }

}