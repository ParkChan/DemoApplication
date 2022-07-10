package com.example.demo.coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CoroutineFlowUnitTest {

    val flow = flow<Int> {
        for (i in 0..10) {
            emit(i)
            delay(100)
        }
    }

    @Test
    fun `데이터를 발행하는 시간보다 처리하는 시간이 더 긴 경우 collectLastest를 사용하면 마지막 데이터만이 소비됩니다`() = runBlocking {
        flow.collectLatest {
            delay(1_000)
            println("number >> $it")
        }
    }

    @Test
    fun `한 번 시작된 데이터 소비는 끝날때까지 하고, 데이터 소비가 끝난 시점의 가장 최신 데이터를 다시 소비됩니다`() = runBlocking {

        flow.onEach {
            println("number >> emit $it")
        }.conflate().collect {
            delay(5_000)
            println("number >> consume $it")
        }
    }
}
