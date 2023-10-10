package com.example.demo.domain.repository.bookmark

import com.example.demo.domain.entity.response.BookmarkDto
import javax.inject.Inject

class BookmarkUseCaseImpl @Inject constructor(
    private val repository: BookmarkRepository
) : BookmarkUseCase {
    override suspend fun invoke() = runCatching {
        repository.fetchBookmark()
    }
}

interface BookmarkUseCase {
    suspend fun invoke(): Result<List<BookmarkDto>>
}