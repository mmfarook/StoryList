package com.example.storylist.di.module

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.example.storylist.database.AppDatabase
import com.example.storylist.database.StoryItemsDao
import com.example.storylist.interceptors.RequestInterceptor
import com.example.storylist.repository.StoryRepository
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideStoryRepository(retrofit: Retrofit, storyItemsDao: StoryItemsDao): StoryRepository {
        return StoryRepository(retrofit, storyItemsDao)
    }

    @Provides
    @Singleton
    internal fun providesRequestInterceptor(): RequestInterceptor {
        return RequestInterceptor()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(requestInterceptor: RequestInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(requestInterceptor);
        return httpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://demo9639618.mockable.io")
            .build()
    }



    @Provides
    @Singleton
    fun provideDatabase(@NonNull application: Application): AppDatabase {
        return Room.databaseBuilder(application.applicationContext, AppDatabase::class.java, "myDB")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideStoryItemsDao(@NonNull appDatabase: AppDatabase): StoryItemsDao {
        return appDatabase.storyItemsDao()
    }
}
