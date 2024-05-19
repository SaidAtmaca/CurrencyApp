package com.saidatmaca.currencyapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saidatmaca.currencyapp.core.common.Constants
import com.saidatmaca.currencyapp.data.local.entity.CoinFavModel
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoinList(list: List<CoinFavModel>)

    @Query("SELECT * FROM ${Constants.COIN_TABLE}")
    fun getCoinListLive() : Flow<List<CoinFavModel>>


    @Query("SELECT * FROM ${Constants.COIN_TABLE}")
    suspend fun getCoinList() : List<CoinFavModel>
    @Query("DELETE FROM ${Constants.COIN_TABLE}")
    suspend fun deleteCoinTable()


}