package com.yefimoyevhen.exchange.util

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesManager
@Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    companion object {
        private const val REQUEST_TIMESTAMP_KEY = "request_timestamp"
        private const val EXCHANGE_TIMESTAMP_KEY = "exchange_timestamp"
        const val DEF_TIMESTAMP_VALUE = 0L
    }

    fun refreshRequestTimestamp(timestamp: Long) =
        sharedPreferences.edit().putLong(REQUEST_TIMESTAMP_KEY, timestamp).apply()


    fun getRequestTimestamp() =
        sharedPreferences.getLong(REQUEST_TIMESTAMP_KEY, DEF_TIMESTAMP_VALUE)

    fun refreshExchangeTimestamp(timestamp: Long) =
        sharedPreferences.edit().putLong(EXCHANGE_TIMESTAMP_KEY, timestamp).apply()

    fun getExchangeTimestamp() =
        sharedPreferences.getLong(EXCHANGE_TIMESTAMP_KEY, DEF_TIMESTAMP_VALUE)
}
