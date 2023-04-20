package com.example.demo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.domain.repository.CountUseCase
import com.example.demo.domain.repository.CountUseCaseImpl
import com.facebook.stetho.common.LogUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: CountUseCase
) : ViewModel() {

    var from: String = ""
    init {
        Timber.d("CHAN >>> MainViewModel init")
        CoroutineScope(Dispatchers.IO).launch {
            useCase.countFlow.collect { number ->
                Timber.d("CHAN >>> receiveAsFlow $from $number")
            }
        }
    }
    fun start(from: String) {
        this.from = from
        CoroutineScope(Dispatchers.IO).launch {
            useCase.startCount()
        }
    }
}