package com.example.demo.data.source

import com.example.demo.data.response.BookmarkResponse

interface TestDataSource {
    suspend fun fetchBookmark(): BookmarkResponse
}