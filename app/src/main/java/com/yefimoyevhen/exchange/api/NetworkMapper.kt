package com.yefimoyevhen.exchange.api

import com.yefimoyevhen.exchange.model.ExchangeListDTO

fun ExchangeNetworkEntity.toExchangeListDTO(): List<ExchangeListDTO> {
    val exchangeEntities = ArrayList<ExchangeListDTO>()
    rates.keys.forEach { key ->
        exchangeEntities.add(
            ExchangeListDTO(
                date = date,
                countryCode = key,
                baseCountryCode = base,
                exchangeRate = rates[key]!!,
                timestamp = timestamp
            )
        )
    }
    return exchangeEntities
}