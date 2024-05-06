package com.djcdev.practicas.data.network

import android.app.Application
import co.infinum.retromock.Retromock
import com.djcdev.practicas.data.RepositoryImpl
import com.djcdev.practicas.data.database.FacturasDataBase
import com.djcdev.practicas.domain.Repository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
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
    @Singleton
    fun provideApiService(retrofit: Retrofit):ApiService{
        return retrofit.create(ApiService::class.java)
    }


    @Provides
    fun provideRepository (apiService: ApiService, db: FacturasDataBase, mockService: MockService):Repository{
        return RepositoryImpl(apiService, db, mockService)
    }

    @Provides
    @Singleton
    fun provideRetromock(retrofit: Retrofit, app :Application):Retromock{
        return Retromock.Builder()
            .retrofit(retrofit)
            .defaultBodyFactory(app.baseContext.assets::open)
            .build()
    }



    @Provides
    @Singleton
    fun provideMockService(retromock: Retromock):MockService{
        return retromock.create(MockService::class.java)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideRemoteConfig():FirebaseRemoteConfig{
        val configSettings : FirebaseRemoteConfigSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 60
        }

        val firebaseConfig :FirebaseRemoteConfig = Firebase.remoteConfig
        firebaseConfig.setConfigSettingsAsync(configSettings)
        firebaseConfig.setDefaultsAsync(mapOf("change_style" to false, "show_list" to true))//En caso de que no funcione internet

        return firebaseConfig
    }


}