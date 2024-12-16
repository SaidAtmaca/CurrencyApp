package com.saidatmaca.data.repository

import android.util.Log
import com.saidatmaca.common.Resource
import com.saidatmaca.data.local.RoomDatabaseDao
import com.saidatmaca.domain.repository.AppRepository
import com.saidatmaca.model.AllCoinResponse
import com.saidatmaca.model.CoinFavModel
import com.saidatmaca.model.HistoryApiResponse
import com.saidatmaca.network.remote.APIService
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okio.IOException


class AppRepositoryImpl(
    private val apiService: APIService,
    private val dao: RoomDatabaseDao
) : AppRepository {


    override fun getAllCryptoData(
        onStart: () -> Unit,
        onComplete: () -> Unit,
        onError: (String?) -> Unit
    ): Flow<AllCoinResponse> = flow {


        val apiResponse = apiService.getAllCryptoData()

        apiResponse.suspendOnSuccess {

            emit(data)

        }.onFailure {

            onError(message())
        }
    }.onStart { onStart() }.onCompletion { onComplete() }

    override fun getCryptoHistoryPrice(coinId: String): Flow<Resource<HistoryApiResponse>> = flow {
        emit(Resource.Loading())
        try {

            val apiResponse = apiService.getCryptoHistoryPrice(coinId)

            Log.e("apiResponseLog4", apiResponse.status.toString())
            Log.e("apiResponseLog5", apiResponse.data.toString())

            if (apiResponse.status.equals("success")) {
                emit(Resource.Success(apiResponse))
            } else {
                emit(Resource.Error("Data Failed"))
            }

        } catch (e: IOException) {
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
        } catch (e: IOException) {

            e.printStackTrace()
        }
    }

    override fun deleteCoinTable() {
        try {
            CoroutineScope(Dispatchers.IO)
                .launch {
                    dao.deleteCoinTable()
                }
        } catch (e: IOException) {

            e.printStackTrace()
        }
    }

    override fun getCoinListLive(): Flow<List<CoinFavModel>> {

        return dao.getCoinListLive()

    }

    override fun getCoinList(): Flow<List<CoinFavModel>> = flow {
        try {

            val list = dao.getCoinList()
            Log.e("coinListLog1", list.toString())

            emit(list)


        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}