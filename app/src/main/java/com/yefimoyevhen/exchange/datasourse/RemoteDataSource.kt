package com.yefimoyevhen.exchange.datasourse

import com.yefimoyevhen.exchange.api.ExchangeApi
import javax.inject.Inject

class RemoteDataSource
@Inject constructor(
    private val api: ExchangeApi
) {
    suspend fun getAllNetworkEntries(data: String) =
        api.getAllExchanges(data)

    suspend fun getNetworkEntry(date: String, countryCode: String) =
        api.getExchangeByCountryCode(date, countryCode)
}