package com.yefimoyevhen.exchange.database

import com.yefimoyevhen.exchange.model.ExchangeListDTO

fun List<ExchangeListDTO>.toExchangeCashEntity(): List<ExchangeCashEntity> {
    val cashEntities = ArrayList<ExchangeCashEntity>()
    forEach { entity ->
        cashEntities.add(
            ExchangeCashEntity(
                date = entity.date,
                countryCode = entity.countryCode,
                baseCountryCode = entity.baseCountryCode,
                exchangeRate = entity.exchangeRate,
                timestamp = entity.timestamp
            )
        )
    }
    return cashEntities
}

fun List<ExchangeCashEntity>.toExchangeListDTO(): List<ExchangeListDTO> {
    val entities = ArrayList<ExchangeListDTO>()
    forEach { entity ->
        entities.add(
            ExchangeListDTO(
                date = entity.date,
                countryCode = entity.countryCode,
                baseCountryCode = entity.baseCountryCode,
                exchangeRate = entity.exchangeRate,
                timestamp = entity.timestamp
            )
        )
    }
    return entities
}