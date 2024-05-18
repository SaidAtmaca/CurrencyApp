package com.saidatmaca.currencyapp.domain.use_case

import com.saidatmaca.currencyapp.core.utils.Resource
import com.saidatmaca.currencyapp.domain.model.ApiResponse
import com.saidatmaca.currencyapp.domain.model.HistoryApiResponse
import com.saidatmaca.currencyapp.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow


class CryptoUseCase (
    private val repository : AppRepository
){


    fun getAllCryptoData() : Flow<Resource<ApiResponse>>{

        return repository.getAllCryptoData()
    }

    fun getCryptoPriceHistory(coinId:String) : Flow<Resource<HistoryApiResponse>>{

        return repository.getCryptoHistoryPrice(coinId)
    }

}