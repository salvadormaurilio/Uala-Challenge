package com.example.ualachallenge.di

import android.app.Application
import com.example.ualachallenge.BuildConfig
import com.example.ualachallenge.core.coroutines.CoroutinesDispatchers
import com.example.ualachallenge.data.CountriesRepository
import com.example.ualachallenge.data.CountriesRepositoryImpl
import com.example.ualachallenge.data.datasource.local.CountriesLocalDataSource
import com.example.ualachallenge.data.datasource.local.CountriesLocalDataSourceImpl
import com.example.ualachallenge.data.datasource.local.room.CountriesDao
import com.example.ualachallenge.data.datasource.local.room.CountriesRoomDatabase
import com.example.ualachallenge.data.datasource.remote.CountriesRemoteDataSource
import com.example.ualachallenge.data.datasource.remote.CountriesRemoteDataSourceImpl
import com.example.ualachallenge.data.datasource.remote.retrofit.CountriesServiceRetrofit
import com.example.ualachallenge.domain.GetCountriesUseCase
import com.example.ualachallenge.domain.GetCountriesUseCaseImpl
import com.google.gson.GsonBuilder
import dagger.Binds
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
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindCountriesRemoteDataSource(
        impl: CountriesRemoteDataSourceImpl
    ): CountriesRemoteDataSource

    @Binds
    abstract fun bindCountriesLocalDataSource(
        impl: CountriesLocalDataSourceImpl
    ): CountriesLocalDataSource

    @Binds
    abstract fun bindCountriesRepository(
        impl: CountriesRepositoryImpl
    ): CountriesRepository

    @Binds
    abstract fun bindGetCountriesUseCase(
        impl: GetCountriesUseCaseImpl
    ): GetCountriesUseCase

    companion object {
        @Provides
        @Singleton
        fun provideCoroutinesDispatchers() = CoroutinesDispatchers()

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor())
                .build()

        @Provides
        @Singleton
        fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .build()

        @Provides
        @Singleton
        fun provideCountriesServiceRetrofit(retrofit: Retrofit): CountriesServiceRetrofit =
            retrofit.create(CountriesServiceRetrofit::class.java)

        @Provides
        @Singleton
        fun provideCountriesRoomDatabase(application: Application): CountriesRoomDatabase =
            CountriesRoomDatabase.getInstance(application)

        @Provides
        fun provideCountriesDao(db: CountriesRoomDatabase): CountriesDao = db.countriesDao()
    }
}
