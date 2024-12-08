package com.saidatmaca.domain.repository

import com.saidatmaca.common.Resource
import com.saidatmaca.model.ApiResponse
import com.saidatmaca.model.CoinFavModel
import com.saidatmaca.model.HistoryApiResponse
import kotlinx.coroutines.flow.Flow

interface AppRepository {


    fun getAllCryptoData(): Flow<Resource<ApiResponse>>


    fun getCryptoHistoryPrice(coinId: String): Flow<Resource<HistoryApiResponse>>

    fun insertCoinList(list: List<CoinFavModel>)
    fun deleteCoinTable()
    fun getCoinListLive(): Flow<List<CoinFavModel>>
    fun getCoinList(): Flow<List<CoinFavModel>>

}