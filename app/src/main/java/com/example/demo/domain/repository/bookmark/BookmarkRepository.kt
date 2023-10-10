package com.example.demo.domain.repository.bookmark

import com.example.demo.domain.entity.response.BookmarkDto

interface BookmarkRepository {
    suspend fun fetchBookmark(): List<BookmarkDto>
}