package com.example.demo.data.di

import com.example.demo.data.TestRepositoryImpl
import com.example.demo.data.source.MockDataSourceImpl
import com.example.demo.data.source.TestDataSource
import com.example.demo.domain.repository.TestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class TestModule {

    @Binds
    @Singleton
    abstract fun bindsTestDataSource(
        mockDataSourceImpl: MockDataSourceImpl
    ): TestDataSource

    @Binds
    @Singleton
    abstract fun bindsMovieSearchRepository(
        testRepositoryImpl: TestRepositoryImpl
    ): TestRepository

}