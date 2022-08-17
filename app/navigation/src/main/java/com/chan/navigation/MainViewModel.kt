package com.chan.navigation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _testLiveData = MutableLiveData<Event<Boolean>>()
    val testLiveData = _testLiveData

    fun doTest(){
        _testLiveData.emit(true)
    }

}