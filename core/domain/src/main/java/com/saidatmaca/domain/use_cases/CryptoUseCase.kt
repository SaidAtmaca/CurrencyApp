package com.saidatmaca.domain.use_cases

import com.saidatmaca.common.Resource
import com.saidatmaca.domain.model.ApiResponse
import com.saidatmaca.domain.model.CoinFavModel
import com.saidatmaca.domain.model.HistoryApiResponse
import com.saidatmaca.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow


class CryptoUseCase (
    private val repository : AppRepository
) {


    fun getAllCryptoData(): Flow<Resource<ApiResponse>> {

        return repository.getAllCryptoData()
    }

    fun getCryptoPriceHistory(coinId: String): Flow<Resource<HistoryApiResponse>> {

        return repository.getCryptoHistoryPrice(coinId)
    }

    fun insertCoinList(list: List<CoinFavModel>){
        repository.insertCoinList(list)
    }


    val coinFlow :Flow<List<CoinFavModel>> =repository.getCoinListLive()


    fun getCoinList() : Flow<List<CoinFavModel>>{
        return repository.getCoinList()
    }


    fun deleteCoinList(){
        return repository.deleteCoinTable()
    }

}
