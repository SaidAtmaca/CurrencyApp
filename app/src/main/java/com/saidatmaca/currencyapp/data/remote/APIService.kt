package com.saidatmaca.currencyapp.data.remote

import com.saidatmaca.currencyapp.domain.model.ApiResponse
import retrofit2.http.GET

interface APIService {


    @GET("coins") // Gerçek endpoint URL'i ile değiştirin
    suspend fun getAllCryptoData(): ApiResponse
}