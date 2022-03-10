package com.example.demo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.demo.util.SingleLiveEvent
import com.example.demo.util.ThemeType

class MainViewModel : ViewModel() {
    private val _themeType = SingleLiveEvent<ThemeType>()
    val themeType: LiveData<ThemeType> get() = _themeType

    fun selectTheme(type: ThemeType) {
        _themeType.value = type
    }
}