package com.saidatmaca.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saidatmaca.domain.model.CoinFavModel

@Database(entities = [CoinFavModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val roomDao : RoomDatabaseDao

}