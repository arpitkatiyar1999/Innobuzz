package com.example.innobuzzapp.di

import android.content.Context
import androidx.room.Room
import com.example.innobuzzapp.local_db.database.AppDatabase
import com.example.innobuzzapp.remote.api_service.ApiService
import com.example.innobuzzapp.ui.main.repo.MainRepo
import com.example.innobuzzapp.ui.main.repo.MainRepoImpl
import com.example.innobuzzapp.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideRetrofit(BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory.invoke())
            .baseUrl(BASE_URL)
            .build()


    @Provides
    fun provideBaseUrl(): String = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideMainRepo(mainRepoImpl: MainRepoImpl): MainRepo = mainRepoImpl

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .build()
    }
}