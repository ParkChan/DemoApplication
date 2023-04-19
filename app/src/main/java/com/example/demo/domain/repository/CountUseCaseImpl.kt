package com.example.demo.domain.repository

import com.example.demo.data.CountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CountUseCaseImpl @Inject constructor(
    private val repository: CountRepository
) {

    val channel = Channel<Int>()
    var sendData: Int = 0

    init {
        Timber.d("CHAN >>> init CountUseCase")
    }


    suspend fun startCount() {
        Timber.d("CHAN >>> init CountUseCase startCount")

        for (i in 0..100) {
            delay(800)
            Timber.d("CHAN >>> CountUseCaseImpl startCount $i")
            channel.send(i)
        }
    }
}