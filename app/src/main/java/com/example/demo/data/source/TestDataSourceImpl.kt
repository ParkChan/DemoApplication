package com.example.demo.data.source

import com.example.demo.data.response.BookmarkResponse
import javax.inject.Inject

class MockDataSourceImpl @Inject constructor(
    private val testService: TestService
):TestDataSource {
    override suspend fun fetchBookmark(): BookmarkResponse {
        return testService.fetchBookmark()
    }
}