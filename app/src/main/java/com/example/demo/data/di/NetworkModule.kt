package com.example.demo.data.di

import com.example.demo.data.MockInterceptor
import com.example.demo.data.source.TestService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesMockInterceptor() = MockInterceptor()

    @Provides
    @Singleton
    fun providesOkHttpClient(
        mockInterceptor: MockInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(mockInterceptor)
            .build()

    @Provides
    @Singleton
    fun providesRetrofitBuild(
        client: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun providesApi(retrofit: Retrofit): TestService =
        retrofit.create(TestService::class.java)
    companion object {
        const val BASE_URL = "https://openapi.naver.com/"
    }

}