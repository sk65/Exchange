package com.yefimoyevhen.exchange.model

data class ExchangeListDTO(
    val date: String,
    val countryCode: String,
    val baseCountryCode: String,
    val exchangeRate: Double,
    val timestamp: Long
)
