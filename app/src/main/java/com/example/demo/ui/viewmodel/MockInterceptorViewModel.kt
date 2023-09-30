package com.example.demo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.data.response.BookmarkItem
import com.example.demo.domain.entity.response.BookmarkEntity
import com.example.demo.domain.repository.FetchBookmarkUseCase
import com.example.demo.ui.util.PerformanceTestUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class MockInterceptorViewModel @Inject constructor(
    private val fetchBookmarkUseCase: FetchBookmarkUseCase
) : ViewModel() {

    private var job: Job? = null

    private val _bookmarkList = MutableLiveData<List<BookmarkEntity>>()
    val bookmarkList: LiveData<List<BookmarkEntity>> = _bookmarkList

    fun fetchBookmarkList() = viewModelScope.launch {
        runCatching {
            val result = fetchBookmarkUseCase.invoke()
            Timber.d("CHAN >>> 성공 $result")
        }.onFailure {
            Timber.d("CHAN >>> 실패 ${it.message}")
        }
    }
}