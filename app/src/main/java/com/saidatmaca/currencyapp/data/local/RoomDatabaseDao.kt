package com.saidatmaca.currencyapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saidatmaca.currencyapp.data.local.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user : User)

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers() : List<User>

    @Query("DELETE FROM user_table")
    suspend fun deleteUserTable()

    @Query("SELECT * FROM user_table")
    fun getUserLive(): Flow<List<User>>



}