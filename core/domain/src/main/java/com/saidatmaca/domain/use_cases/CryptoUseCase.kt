package com.saidatmaca.domain.use_cases

import com.saidatmaca.common.Resource
import com.saidatmaca.domain.repository.AppRepository
import com.saidatmaca.model.AllCoinResponse
import com.saidatmaca.model.CoinFavModel
import com.saidatmaca.model.HistoryApiResponse
import kotlinx.coroutines.flow.Flow


class CryptoUseCase (
    private val repository : AppRepository
) {


    fun getAllCryptoData(
        onStart : ()->Unit,
        onComplete:()->Unit,
        onError:(String?)->Unit
    ): Flow<AllCoinResponse> {

        return repository.getAllCryptoData(
            onStart,
            onComplete,
            onError
        )
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
