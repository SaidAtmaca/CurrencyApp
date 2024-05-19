package com.saidatmaca.currencyapp.di

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.saidatmaca.currencyapp.BuildConfig
import com.saidatmaca.currencyapp.core.common.Constants
import com.saidatmaca.currencyapp.core.common.Constants.TIME_OUT_RETROFIT
import com.saidatmaca.currencyapp.data.local.AppDatabase
import com.saidatmaca.currencyapp.data.remote.APIService
import com.saidatmaca.currencyapp.data.repository.AppRepositoryImpl
import com.saidatmaca.currencyapp.domain.repository.AppRepository
import com.saidatmaca.currencyapp.domain.use_case.CryptoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    /**------------------------------------------------------------------- APP PROVIDES  -----------------------------------------------***/
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
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

        Log.e("saedasdawdwad", BuildConfig.BUILD_TYPE.toString())
        val builder =
            OkHttpClient
                .Builder()
                .readTimeout(
                    TIME_OUT_RETROFIT,
                    TimeUnit.SECONDS)
                .connectTimeout(
                TIME_OUT_RETROFIT,
                TimeUnit.SECONDS
            )
        builder.interceptors()
            .add(interceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: AppDatabase,
        api: APIService
    ): AppRepository {
        return AppRepositoryImpl(api, db.roomDao)
    }


    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app, AppDatabase::class.java, Constants.ROOM_DB_NAME
        ).build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): APIService {
        return retrofit.create(APIService::class.java)
    }


    /**------------------------------------------------------------------- USECASE PROVIDES  -----------------------------------------------***/





    @Provides
    @Singleton
    fun provideCrytoUseCase(repository: AppRepository): CryptoUseCase {
        return CryptoUseCase(repository)
    }






}