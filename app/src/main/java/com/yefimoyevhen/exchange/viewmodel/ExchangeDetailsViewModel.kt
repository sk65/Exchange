package com.yefimoyevhen.exchange.viewmodel

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.yefimoyevhen.exchange.interactors.FetchWeeklySummaryUseCase
import com.yefimoyevhen.exchange.model.ExchangeDetailsDTO
import com.yefimoyevhen.exchange.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeDetailsViewModel
@Inject
constructor(
    private val fetchWeeklySummaryUseCase: FetchWeeklySummaryUseCase
) : ViewModel() {
    private val _dataState = MutableLiveData<DataState<ExchangeDetailsDTO>>()
    val dataState: LiveData<DataState<ExchangeDetailsDTO>>
        get() = _dataState

    fun getWeeklySummary(countryCode: String) {
        viewModelScope.launch {
            fetchWeeklySummaryUseCase.fetchData(countryCode)
                .onEach { dataState ->
                    when (dataState) {
                        is DataState.Error -> _dataState.postValue(DataState.Error(dataState.message))
                        DataState.Loading -> _dataState.postValue(DataState.Loading)
                        is DataState.Success -> {
                            val data = dataState.data
                            val labelsNames = ArrayList<String>()
                            val barEntries = ArrayList<BarEntry>()
                            for (i in data.indices) {
                                labelsNames.add(data[i].first)
                                barEntries.add(BarEntry(i.toFloat(), data[i].second))
                            }
                            val barDataSet = BarDataSet(barEntries, "Label")
                                .apply {
                                    colors = ColorTemplate.MATERIAL_COLORS.toList()
                                    valueTextColor = Color.BLACK
                                }
                            val barData = BarData(barDataSet)
                            _dataState.postValue(
                                DataState.Success(
                                    ExchangeDetailsDTO(
                                        labelsNames,
                                        barData
                                    )
                                )
                            )
                        }
                    }
                }.launchIn(viewModelScope)
        }
    }
}