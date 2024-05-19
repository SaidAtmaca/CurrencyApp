package com.saidatmaca.currencyapp.domain.repository

import com.saidatmaca.currencyapp.core.utils.Resource
import com.saidatmaca.currencyapp.data.local.entity.CoinFavModel
import com.saidatmaca.currencyapp.domain.model.ApiResponse
import com.saidatmaca.currencyapp.domain.model.HistoryApiResponse
import kotlinx.coroutines.flow.Flow


interface AppRepository {


    fun getAllCryptoData() : Flow<Resource<ApiResponse>>


    fun getCryptoHistoryPrice(coinId:String) : Flow<Resource<HistoryApiResponse>>

    fun insertCoinList(list: List<CoinFavModel>)
    fun deleteCoinTable()
    fun getCoinListLive() : Flow<List<CoinFavModel>>
    fun getCoinList() : Flow<List<CoinFavModel>>

}