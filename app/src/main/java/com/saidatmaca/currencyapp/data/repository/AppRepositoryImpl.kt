package com.saidatmaca.currencyapp.data.repository

import android.util.Log
import com.saidatmaca.currencyapp.core.utils.Resource
import com.saidatmaca.currencyapp.data.local.RoomDatabaseDao
import com.saidatmaca.currencyapp.data.local.entity.CoinFavModel
import com.saidatmaca.currencyapp.data.remote.APIService
import com.saidatmaca.currencyapp.domain.model.ApiResponse
import com.saidatmaca.currencyapp.domain.model.HistoryApiResponse
import com.saidatmaca.currencyapp.domain.repository.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
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

            if (apiResponse.status.equals("success")){
                emit(Resource.Success(apiResponse))
            }else{
                emit(Resource.Error("Data Failed"))
            }

        }catch (e:IOException){
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getCryptoHistoryPrice(coinId:String): Flow<Resource<HistoryApiResponse>> = flow {
        emit(Resource.Loading())
        try {

            val apiResponse = apiService.getCryptoHistoryPrice(coinId)

            Log.e("apiResponseLog4",apiResponse.status.toString())
            Log.e("apiResponseLog5",apiResponse.data.toString())

            if (apiResponse.status.equals("success")){
                emit(Resource.Success(apiResponse))
            }else{
                emit(Resource.Error("Data Failed"))
            }

        }catch (e:IOException){
            emit(Resource.Error(e.message.toString()))
        }

    }



    override fun insertCoinList(list: List<CoinFavModel>) {
        try {
            CoroutineScope(Dispatchers.IO)
                .launch {
                    dao.deleteCoinTable()
                    dao.insertCoinList(list)
                }
        }catch (e:IOException){

            e.printStackTrace()
        }
    }

    override fun deleteCoinTable() {
        try {
            CoroutineScope(Dispatchers.IO)
                .launch {
                    dao.deleteCoinTable()
                }
        }catch (e:IOException){

            e.printStackTrace()
        }
    }

    override fun getCoinListLive(): Flow<List<CoinFavModel>> {

        return dao.getCoinListLive()

       /* try {

            val list = dao.getConList()
            Log.e("coinListLog1",list.toString())

            emit(list)



        }catch (e:IOException){
            e.printStackTrace()
        }*/
    }

    override fun getCoinList(): Flow<List<CoinFavModel>> = flow {
         try {

              val list = dao.getCoinList()
              Log.e("coinListLog1",list.toString())

              emit(list)



          }catch (e:IOException){
              e.printStackTrace()
          }
    }


}