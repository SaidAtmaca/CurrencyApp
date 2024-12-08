package com.saidatmaca.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saidatmaca.model.CoinFavModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinList(list: List<CoinFavModel>)

    @Query("SELECT * FROM ${"COIN_TABLE"}")
    fun getCoinListLive() : Flow<List<CoinFavModel>>


    @Query("SELECT * FROM ${"COIN_TABLE"}")
    suspend fun getCoinList() : List<CoinFavModel>
    @Query("DELETE FROM ${"COIN_TABLE"}")
    suspend fun deleteCoinTable()


}