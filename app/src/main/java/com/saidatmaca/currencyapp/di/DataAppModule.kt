package com.saidatmaca.currencyapp.di

import android.app.Application
import androidx.room.Room
import com.saidatmaca.common.Constants
import com.saidatmaca.data.local.AppDatabase
import com.saidatmaca.data.repository.AppRepositoryImpl
import com.saidatmaca.domain.repository.AppRepository
import com.saidatmaca.domain.use_cases.CryptoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataAppModule {



    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: AppDatabase,
        api: com.saidatmaca.network.remote.APIService
    ): AppRepository {
        return AppRepositoryImpl(api, db.roomDao)
    }


    @Provides
    @Singleton
    fun provideCryptoUseCase(repository: AppRepository): CryptoUseCase {
        return CryptoUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app, AppDatabase::class.java, Constants.ROOM_DB_NAME
        ).build()
    }



}

