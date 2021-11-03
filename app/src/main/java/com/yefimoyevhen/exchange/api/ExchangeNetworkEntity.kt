package com.yefimoyevhen.exchange.api

data class ExchangeNetworkEntity(
    val base: String,
    val date: String,
    val historical: Boolean,
    val rates: Map<String, Double>,
    val success: Boolean,
    val timestamp: Long
)