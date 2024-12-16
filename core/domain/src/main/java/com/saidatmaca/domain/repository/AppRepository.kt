package com.saidatmaca.domain.repository

import com.saidatmaca.common.Resource
import com.saidatmaca.model.AllCoinResponse
import com.saidatmaca.model.CoinFavModel
import com.saidatmaca.model.HistoryApiResponse
import kotlinx.coroutines.flow.Flow

interface AppRepository {


    fun getAllCryptoData(
        onStart : ()->Unit,
        onComplete:()->Unit,
        onError:(String?)->Unit
    ): Flow<AllCoinResponse>


    fun getCryptoHistoryPrice(coinId: String): Flow<Resource<HistoryApiResponse>>

    fun insertCoinList(list: List<CoinFavModel>)
    fun deleteCoinTable()
    fun getCoinListLive(): Flow<List<CoinFavModel>>
    fun getCoinList(): Flow<List<CoinFavModel>>

}