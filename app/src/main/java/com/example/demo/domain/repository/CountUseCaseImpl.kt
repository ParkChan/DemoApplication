package com.example.demo.domain.repository

import com.example.demo.data.CountRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class CountUseCaseImpl @Inject constructor(
    private val repository: CountRepository
): CountUseCase {

    init {
        Timber.d("CHAN >>> init CountUseCase")
    }

    val channel = Channel<Int>()
    var sendData: Int = 0

    override suspend fun startCount() {
        repeat(100) {
            delay(1_000)
            channel.send(sendData++)
        }
    }
}