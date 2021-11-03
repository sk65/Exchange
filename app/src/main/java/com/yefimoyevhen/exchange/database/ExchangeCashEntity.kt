package com.yefimoyevhen.exchange.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExchangeCashEntity(
    @PrimaryKey(autoGenerate = true)
    val exchangeId: Long? = null,
    val date: String,
    val countryCode: String,
    val baseCountryCode: String,
    val exchangeRate: Double,
    val timestamp: Long
)