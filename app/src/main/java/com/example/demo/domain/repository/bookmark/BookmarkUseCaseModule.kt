package com.example.demo.domain.repository.bookmark

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BookmarkUseCaseModule {
    @Binds
    @Singleton
    abstract fun bindsBookmarkUseCase(bookmarkUseCase: BookmarkUseCaseImpl): BookmarkUseCase
}