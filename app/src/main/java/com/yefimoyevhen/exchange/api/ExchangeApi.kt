package com.yefimoyevhen.exchange.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExchangeApi {
    @GET("{data}")
    suspend fun getAllExchanges(@Path("data") data: String): ExchangeNetworkEntity

    @GET("{data}")
    suspend fun getExchangeByCountryCode(
        @Path("data") data: String,
        @Query("symbols") symbols: String
    ): ExchangeNetworkEntity


}