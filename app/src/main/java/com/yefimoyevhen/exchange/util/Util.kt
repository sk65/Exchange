package com.yefimoyevhen.exchange.util

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.round

fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}

fun isRefreshTimeout(oldTimestamp: Long): Boolean {
    val currentTimestampMin = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())
    val oldTimestampMin = TimeUnit.MILLISECONDS.toMinutes(oldTimestamp)
    return oldTimestampMin - currentTimestampMin > MINUTES_TIMEOUT
}

fun getCurrentDate(date: Date? = null): String =
    SimpleDateFormat(DATE_FORMAT).format(date ?: Date())

fun getDaysAgo(daysAgo: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
    return calendar.time
}

fun Date.getDayOfWeek() = SimpleDateFormat("EE").format(this)


