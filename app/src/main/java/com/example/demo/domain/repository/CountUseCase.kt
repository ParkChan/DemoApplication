package com.example.demo.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface CountUseCase {

    val countFlow: StateFlow<Int>
    var isActiveFlow: Boolean
    suspend fun startCount()
}