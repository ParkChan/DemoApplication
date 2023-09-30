package com.example.demo.data

import com.example.demo.data.source.TestDataSource
import com.example.demo.domain.entity.response.BookmarkEntity
import com.example.demo.domain.repository.TestRepository
import javax.inject.Inject

internal class TestRepositoryImpl @Inject constructor(
    private val testDataSource: TestDataSource
) : TestRepository {
    override suspend fun fetchBookmark(): List<BookmarkEntity> {
        return testDataSource.fetchBookmark().bookmarkList.map { it.mapToDomain() }
    }

}