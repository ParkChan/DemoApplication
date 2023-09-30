package com.example.demo.domain.repository

import javax.inject.Inject

class FetchBookmarkUseCase @Inject constructor(
    private val repository: TestRepository
) {
    suspend fun invoke() = runCatching {
        repository.fetchBookmark()
    }
}