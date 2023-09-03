package com.example.demo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.ui.util.PerformanceTestUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LogEventViewModel @Inject constructor() : ViewModel() {

    private var job: Job? = null
    private val countFlow = MutableStateFlow(0)
    private var isActiveFlow: Boolean = false

    private val _tps = MutableLiveData<Int>()
    val tps: LiveData<Int> = _tps

    fun start() {
        //job?.cancel()
        viewModelScope.launch {
            startCount()
            countFlow.collect { number ->
                Timber.d("CHAN >>> $number")
                PerformanceTestUtil.startTpsMonitoring { count ->
                    _tps.postValue(count)
                }
            }
        }
    }
    private fun startCount() {

        if (isActiveFlow.not()) {
            isActiveFlow = true

            CoroutineScope(Dispatchers.IO).launch {
                for (i in 0..100) {
                    delay(COUNT_DELAY_TIME)
                    countFlow.value = i
                }
            }

        } else {
            Timber.d("CHAN >>> CountUseCaseImpl already startCount..")
        }
    }

    override fun onCleared() {
        PerformanceTestUtil.initTpsMonitoring()
        super.onCleared()
    }
    companion object {
        private const val COUNT_DELAY_TIME = 200L
    }
}