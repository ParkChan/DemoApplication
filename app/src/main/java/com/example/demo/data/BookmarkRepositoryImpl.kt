package com.example.demo.data

import com.example.demo.data.source.TestDataSource
import com.example.demo.domain.entity.response.BookmarkDto
import com.example.demo.domain.repository.bookmark.BookmarkRepository
import javax.inject.Inject

internal class BookmarkRepositoryImpl @Inject constructor(
    private val testDataSource: TestDataSource
) : BookmarkRepository {
    override suspend fun fetchBookmark(): List<BookmarkDto> {
        return testDataSource.fetchBookmark().bookmarkList.map { it.mapToDomain() }
    }

}