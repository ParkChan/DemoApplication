package com.example.demo.domain.repository.sample

import kotlinx.coroutines.flow.StateFlow

interface CountUseCase {

    val countFlow: StateFlow<Int>
    var isActiveFlow: Boolean
    suspend fun startCount()
}