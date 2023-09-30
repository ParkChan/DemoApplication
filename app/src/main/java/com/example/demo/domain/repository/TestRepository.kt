package com.example.demo.domain.repository

import com.example.demo.domain.entity.response.BookmarkEntity

interface TestRepository {
    suspend fun fetchBookmark(): List<BookmarkEntity>
}