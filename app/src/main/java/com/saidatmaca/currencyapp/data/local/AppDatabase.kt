package com.saidatmaca.currencyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saidatmaca.currencyapp.data.local.entity.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val roomDao : RoomDatabaseDao

}