package com.saidatmaca.network.remote

import com.saidatmaca.model.AllCoinResponse
import com.saidatmaca.model.HistoryApiResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {


    @GET("coins")
    suspend fun getAllCryptoData(): ApiResponse<AllCoinResponse>


    @GET("coin/{id}/history?timePeriod=1y")
    suspend fun getCryptoHistoryPrice(@Path("id") coinId: String): HistoryApiResponse
}