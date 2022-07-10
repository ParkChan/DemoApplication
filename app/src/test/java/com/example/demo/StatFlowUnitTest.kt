package com.example.demo

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import org.junit.Test
import timber.log.Timber

class StatFlowUnitTest {

    private val _stateFlow = MutableStateFlow(99)
    val stateFlow = _stateFlow

    @Test
    fun `MutableStateFlow 데이터에 값을 전달해보기`() = runBlocking {
        //value 속성을 사용시
        _stateFlow.value = 1

        //flow의 기본 함수인 emit()을 사용시
        _stateFlow.emit(1)
    }
}
