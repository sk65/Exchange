package com.yefimoyevhen.exchange.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ExchangeDao {
    @Insert(onConflict = REPLACE)
    suspend fun insertEntry(entry: ExchangeCashEntity): Long

    @Query("SELECT * FROM exchangecashentity WHERE date = :date")
    suspend fun findAllEntries(date: String): List<ExchangeCashEntity>

    @Query("SELECT * FROM exchangecashentity WHERE timestamp = :timestamp")
    suspend fun findAllEntries(timestamp: Long): List<ExchangeCashEntity>

    @Query("SELECT * FROM exchangecashentity WHERE countryCode =:countryCode")
    suspend fun findAllEntriesByCountryCode(countryCode: String): List<ExchangeCashEntity>

    @Query("DELETE FROM exchangecashentity")
    suspend fun deleteAllEntries()
}