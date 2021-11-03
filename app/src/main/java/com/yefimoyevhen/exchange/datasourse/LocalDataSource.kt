package com.yefimoyevhen.exchange.datasourse

import com.yefimoyevhen.exchange.database.ExchangeDao
import com.yefimoyevhen.exchange.database.ExchangeCashEntity
import javax.inject.Inject

class LocalDataSource
@Inject
constructor(
    private val exchangeDao: ExchangeDao
) {
    suspend fun findAllEntries(data: String) = exchangeDao.findAllEntries(data)
    suspend fun findAllEntries(timestamp: Long) = exchangeDao.findAllEntries(timestamp)
    suspend fun deleteAllEntries() = exchangeDao.deleteAllEntries()
    suspend fun insertEntries(entries: List<ExchangeCashEntity>) {
        entries.forEach { entry ->
            exchangeDao.insertEntry(entry)
        }
    }
}