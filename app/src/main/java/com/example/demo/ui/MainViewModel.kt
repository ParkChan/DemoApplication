package com.example.demo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.domain.repository.CountUseCase
import com.facebook.stetho.common.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: CountUseCase
) : ViewModel() {

    init {

    }
    fun start(){
        Timber.d("CHAN >>> MainViewModel init")
        CoroutineScope(Dispatchers.IO).launch {
            useCase.startCount()
        }
    }


    private val _stateFlow = MutableStateFlow(99)
    val stateFlow = _stateFlow

    // state flow에 데이터 전달
    suspend fun startSendDataToStateFlow() {
        repeat(10) {
            _stateFlow.value = it
            delay(250)
        }
    }

    private val _sharedFlow = MutableSharedFlow<Int>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val sharedFlow = _sharedFlow

    // SharedFlow에 데이터 전달
    fun startSendDataToSharedFlow() = viewModelScope.launch {
        repeat(10) {
//            Timber.d("Test CHAN >>> startSendDataToSharedFlow $it")
            //_sharedFlow.value value 지원안함...
            _sharedFlow.emit(it)
            delay(500)
        }
    }
}