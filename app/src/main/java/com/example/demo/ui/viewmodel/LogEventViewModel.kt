package com.example.demo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.ui.util.PerformanceTestUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class LogEventViewModel @Inject constructor() : ViewModel() {

    private var startEventJob: Job? = null

    private val _testFlow = MutableStateFlow(0)
    val testFlow = _testFlow.asStateFlow()

    private val _eventCnt = MutableLiveData<String>()
    val eventCnt: LiveData<String> = _eventCnt

    fun startTestEventCount() {
        startEventJob?.cancel()
        startEventJob = viewModelScope.launch {
            startTestEvent()
        }

        viewModelScope.launch {
            testFlow.collect {
                PerformanceTestUtil.sendEvent({ result ->
                    _eventCnt.value = result
                })
            }
        }
    }
    private suspend fun startTestEvent() {
        withContext(Dispatchers.IO){
            for (count: Int in 0..100) {
                delay(COUNT_DELAY_TIME_10)
                _testFlow.emit(count)
            }
        }
    }

    override fun onCleared() {
        startEventJob?.cancel()
        PerformanceTestUtil.initTpsMonitoring()
        super.onCleared()
    }

    companion object {
        private const val COUNT_DELAY_TIME_10 = 200L
    }
}