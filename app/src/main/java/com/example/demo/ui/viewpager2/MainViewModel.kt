package com.example.demo.ui.viewpager2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    // replay = 0 새로운 구독자에게 이전 이벤트를 전달하지 않음
    // extraBufferCapacity 추가 버퍼를 생성하여 emit 한 데이터가 버퍼에 유지되도록 함
    // 버퍼가 가득찼을 시 오래된 데이터 제거
    private val _systemEvent: MutableSharedFlow<Int> =
        MutableSharedFlow(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val systemEvent = _systemEvent.asSharedFlow()

    fun reportSystemEvent(number: Int) {
        viewModelScope.launch {
            _systemEvent.emit(number)
        }
    }

}