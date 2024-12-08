package com.saidatmaca.network.di

import com.saidatmaca.common.Constants
import com.saidatmaca.network.remote.APIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }


    @Provides
    fun provideOkHttpClient(
    ): OkHttpClient {

        val interceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("x-access-token", "coinrankingea8fbbc917a3c8a7ff1952a072dfd0f47a14559e79cd2908")
                .build()
            chain.proceed(request)
        }

        val builder =
            OkHttpClient
                .Builder()
                .readTimeout(
                    Constants.TIME_OUT_RETROFIT,
                    TimeUnit.SECONDS)
                .connectTimeout(
                    Constants.TIME_OUT_RETROFIT,
                    TimeUnit.SECONDS
                )
        builder.interceptors()
            .add(interceptor)
        return builder.build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }
}