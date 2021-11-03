package com.yefimoyevhen.exchange.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yefimoyevhen.exchange.interactors.FetchAllExchangesUseCase
import com.yefimoyevhen.exchange.model.ExchangeListDTO
import com.yefimoyevhen.exchange.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeListViewModel
@Inject
constructor(
    private val fetchAllExchangesUseCase: FetchAllExchangesUseCase,
) : ViewModel() {
    private val _dataState = MutableLiveData<DataState<List<ExchangeListDTO>>>()
    val dataState: LiveData<DataState<List<ExchangeListDTO>>>
        get() = _dataState

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            fetchAllExchangesUseCase.fetchData()
                .onEach { dataState ->
                    _dataState.value = dataState
                }.launchIn(viewModelScope)
        }
    }

}