package com.example.demo.data.source

import com.example.demo.data.response.BookmarkResponse
import retrofit2.http.GET

interface TestService {
    @GET("/mock_api/bookmark/list")
    suspend fun fetchBookmark(): BookmarkResponse
}