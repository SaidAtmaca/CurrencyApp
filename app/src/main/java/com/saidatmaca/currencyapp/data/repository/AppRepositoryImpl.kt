package com.saidatmaca.currencyapp.data.repository

import android.util.Log
import com.saidatmaca.currencyapp.core.utils.Resource
import com.saidatmaca.currencyapp.data.local.RoomDatabaseDao
import com.saidatmaca.currencyapp.data.local.entity.User
import com.saidatmaca.currencyapp.data.remote.APIService
import com.saidatmaca.currencyapp.domain.model.ApiResponse
import com.saidatmaca.currencyapp.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import okio.IOException

class AppRepositoryImpl(
    private val apiService: APIService,
    private val dao : RoomDatabaseDao
): AppRepository {



    override fun getAllCryptoData(): Flow<Resource<ApiResponse>> = flow {
        emit(Resource.Loading())

        try {

            val apiResponse = apiService.getAllCryptoData()

            Log.e("apiResponseLog1",apiResponse.status.toString())
            Log.e("apiResponseLog2",apiResponse.data.toString())

            if (apiResponse.status.equals("succes")){
                emit(Resource.Success(apiResponse))
            }else{
                emit(Resource.Error("Data Failed"))
            }

        }catch (e:IOException){
            emit(Resource.Error(e.message.toString()))
        }
    }


}