package com.example.demo.domain.repository

import com.example.demo.data.CountRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class CountUseCaseImpl @Inject constructor(
    private val repository: CountRepository
) : CountUseCase {

    init {
        Timber.d("CHAN >>> CountUseCaseImpl init")
    }

    override val countFlow = MutableStateFlow(0)
    override var isActiveFlow: Boolean = false

    override suspend fun startCount() {

        if (isActiveFlow.not()) {
            isActiveFlow = true

            for (i in 0..100) {
                delay(800)
                countFlow.value = i
            }
        } else {
            Timber.d("CHAN >>> CountUseCaseImpl already startCount..")
        }
    }
}