package com.yefimoyevhen.exchange.model

import com.github.mikephil.charting.data.BarData

data class ExchangeDetailsDTO(
    val labelsNames: List<String>,
    val barData: BarData
)
