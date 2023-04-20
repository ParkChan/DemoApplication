package com.example.demo.domain.repository

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.StateFlow

interface CountUseCase {

    val countFlow: StateFlow<Int>
    var isActiveChannel: Boolean
    suspend fun startCount()
}