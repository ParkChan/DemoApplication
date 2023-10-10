package com.example.demo.domain.repository.sample

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CountUseCaseModule {
    @Binds
    @Singleton
    abstract fun bindsCountUseCase(useCase: CountUseCaseImpl): CountUseCase

}