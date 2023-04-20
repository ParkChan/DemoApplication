package com.example.demo.domain.repository

import com.example.demo.data.CountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class CountUseCaseImpl @Inject constructor(
    private val repository: CountRepository
) : CountUseCase {

    init {
        Timber.d("CHAN >>> CountUseCaseImpl init")
    }

    override val countFlow = MutableStateFlow(0)
    override var isActiveChannel: Boolean = false

    override suspend fun startCount() {

        if (isActiveChannel.not()) {
            isActiveChannel = true

            for (i in 0..100) {
                delay(800)
                countFlow.value = i
            }
        }else{
            Timber.d("CHAN >>> CountUseCaseImpl already startCount..")
        }
    }
}