package com.example.demo.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo.domain.entity.response.BookmarkDto
import com.example.demo.domain.repository.bookmark.BookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MockInterceptorViewModel @Inject constructor(
    private val bookmarkUseCase: BookmarkUseCase
) : ViewModel() {

    private val _bookmarkList = MutableLiveData<List<BookmarkDto>>()
    val bookmarkList: LiveData<List<BookmarkDto>> = _bookmarkList

    fun fetchBookmarkList() = viewModelScope.launch {
        runCatching {
            val result = bookmarkUseCase.invoke()
            Timber.d("CHAN >>> 성공 $result")
            _bookmarkList.value = result.getOrNull()
        }.onFailure {
            Timber.d("CHAN >>> 실패 ${it.message}")
        }
    }
}