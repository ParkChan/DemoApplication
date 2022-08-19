package com.chan.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _testLiveData = MutableStateFlow(false)
    val testLiveData: StateFlow<Boolean> = _testLiveData

    fun doTest() {
        viewModelScope.launch {
            _testLiveData.emit(true)
        }
    }

}