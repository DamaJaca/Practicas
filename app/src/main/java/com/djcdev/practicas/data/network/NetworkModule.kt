package com.djcdev.practicas.data.network

import co.infinum.retromock.Retromock
import com.djcdev.practicas.data.RepositoryImpl
import com.djcdev.practicas.data.database.FacturasDataBase
import com.djcdev.practicas.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://viewnextandroid.wiremockapi.cloud")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Provides
    @Singleton
    fun providesInterceptor ():OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    fun provideRepository (apiService: ApiService, db: FacturasDataBase, mockService: MockService):Repository{
        return RepositoryImpl(apiService, db, mockService)
    }

    @Provides
    @Singleton
    fun provideRetromock(retrofit: Retrofit):Retromock{
        return Retromock.Builder()
            .retrofit(retrofit)
            .build()
    }
    @Provides
    fun provideMockService(retromock: Retromock):MockService{
        return retromock.create(MockService::class.java)
    }


}