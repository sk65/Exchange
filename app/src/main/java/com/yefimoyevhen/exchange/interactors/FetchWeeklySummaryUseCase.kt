package com.yefimoyevhen.exchange.interactors

import com.yefimoyevhen.exchange.datasourse.RemoteDataSource
import com.yefimoyevhen.exchange.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchWeeklySummaryUseCase @Inject
constructor(
    private val internetChecker: InternetChecker,
    private val remoteDataSource: RemoteDataSource,
) {
    suspend fun fetchData(countryCode: String): Flow<DataState<List<Pair<String, Float>>>> = flow {
        emit(DataState.Loading)
        if (internetChecker.hasInternetConnection()) {
            try {
                val dataList: MutableList<Pair<String, Float>> = ArrayList()
                repeat(DAYS_LIMIT) { i ->
                    val date = getCurrentDate(getDaysAgo(i))
                    val day = getDaysAgo(i).getDayOfWeek()
                    val exchangeEntity = remoteDataSource.getNetworkEntry(date, countryCode)
                    dataList.add(Pair(day, exchangeEntity.rates[countryCode]!!.toFloat()))
                }
                dataList.reverse()
                emit(DataState.Success(dataList))
            } catch (e: Exception) {
                emit(DataState.Error(e.localizedMessage))
            }
        } else {
            emit(DataState.Error("No exchange rate data is available for the selected currency."))
        }
    }
}