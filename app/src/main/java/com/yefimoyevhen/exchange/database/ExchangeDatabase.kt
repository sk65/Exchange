package com.yefimoyevhen.exchange.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ExchangeCashEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExchangeDatabase: RoomDatabase() {
    abstract fun exchangeDao(): ExchangeDao
    companion object {
        const val DATABASE_NAME = "exchange_db"
    }
}