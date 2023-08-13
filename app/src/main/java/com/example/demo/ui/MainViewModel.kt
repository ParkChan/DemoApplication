package com.example.demo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.domain.repository.CountUseCase
import com.example.demo.ui.util.PerformanceTestUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: CountUseCase
) : ViewModel() {

    var job: Job? = null
    var from: String = ""
    init {
        Timber.d("CHAN >>> MainViewModel init")
        job = viewModelScope.launch {
            useCase.countFlow.collect { number ->
                PerformanceTestUtil.startTpsMonitoring()
                Timber.d("CHAN >>> receiveAsFlow $from $number")
            }
        }
    }
    fun start(from: String) {
        this.from = from
        viewModelScope.launch {
            useCase.startCount()
        }
    }

    override fun onCleared() {
        job?.cancel()
        PerformanceTestUtil.initTpsMonitoring()
        super.onCleared()
    }
}