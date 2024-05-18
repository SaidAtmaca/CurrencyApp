package com.saidatmaca.currencyapp.presentation.ui.detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaikeerthick.composable_graphs.composables.line.model.LineData
import com.saidatmaca.currencyapp.core.common.GlobalValues
import com.saidatmaca.currencyapp.core.common.enums.UIEvent
import com.saidatmaca.currencyapp.core.common.observeUserLive
import com.saidatmaca.currencyapp.core.common.toFormattedDate
import com.saidatmaca.currencyapp.core.utils.Resource
import com.saidatmaca.currencyapp.data.local.entity.User
import com.saidatmaca.currencyapp.domain.model.Coin
import com.saidatmaca.currencyapp.domain.model.HistoryApiResponse
import com.saidatmaca.currencyapp.domain.model.HistoryModel
import com.saidatmaca.currencyapp.domain.use_case.CryptoUseCase
import com.saidatmaca.currencyapp.domain.use_case.UserLiveUseCase
import com.saidatmaca.currencyapp.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val userLiveUseCase: UserLiveUseCase,
    private val cryptoUseCase: CryptoUseCase,
) : ViewModel(){


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _userState : MutableState<User?> = mutableStateOf(null)
    val userState : State<User?> = _userState

    private val _coin : MutableState<Coin?> = mutableStateOf(null)
    val coin : State<Coin?> = _coin

    private val _historyApiResponse : MutableState<HistoryApiResponse?> = mutableStateOf(null)
    val historyApiResponse : State<HistoryApiResponse?> = _historyApiResponse

    private val _historicalPriceList : MutableState<List<HistoryModel>> = mutableStateOf(listOf())
    val historicalPriceList : State<List<HistoryModel>> = _historicalPriceList



    val lineGraphData :SnapshotStateList<LineData> = mutableStateListOf()



    private var job: Job? = null

    fun setCoin(coin: Coin?){
        _coin.value = coin
    }

    fun goToHomeScreen(){
        viewModelScope.launch {
            _eventFlow.emit(UIEvent.Navigate(Screen.HomeScreen.route))
            setCoin(null)
        }
    }

    fun getCryptoHistoricalData(coinId:String){

        job = viewModelScope.launch {
            cryptoUseCase.getCryptoPriceHistory(coinId)
                .onEach {result->
                    when (result) {

                        is Resource.Success -> {
                            GlobalValues.showLoading.postValue(false)
                            Log.e("allCryptoDataa1",result.data.toString())

                            result.data?.let {

                                _historyApiResponse.value = it
                                Log.e("allCryptoHistoryDataa3",it.status.toString())
                                Log.e("allCryptHistoryoDataa4",it.data.toString())


                                _historicalPriceList.value=it.data.history


                               createGraphUI()



                            }



                        }

                        is Resource.Error -> {
                            GlobalValues.showLoading.postValue(false)
                            Log.e("allCryptoDataa2",result.data.toString())
                            _eventFlow.emit(
                                UIEvent.ShowSnackbar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }

                        is Resource.Loading -> {
                            Log.e("allCryptoDataa3",result.data.toString())
                            GlobalValues.showLoading.postValue(true)
                        }
                    }
                }.launchIn(this)
        }

    }

    private fun createGraphUI() {

        var data = arrayListOf<LineData>()

        /*_historicalPriceList.value.forEach {
            data.add(LineData(it.timestamp.toString(),it.price))
        }*/
        val size = _historicalPriceList.value.size

        data.add(LineData(_historicalPriceList.value.get(size/5).timestamp.toFormattedDate(),_historicalPriceList.value.get(size/5).price))
        data.add(LineData(_historicalPriceList.value.get(size/4).timestamp.toFormattedDate(),_historicalPriceList.value.get(size/4).price))
        data.add(LineData(_historicalPriceList.value.get(size/3).timestamp.toFormattedDate(),_historicalPriceList.value.get(size/3).price))
        data.add(LineData(_historicalPriceList.value.get(size/2).timestamp.toFormattedDate(),_historicalPriceList.value.get(size/2).price))
        data.add(LineData(_historicalPriceList.value.get(0).timestamp.toFormattedDate(),_historicalPriceList.value.get(3).price))

        lineGraphData.clear()
        lineGraphData.addAll(data)

        Log.e("linegraphhh",lineGraphData.toList().toString())


    }


    init {
        this.observeUserLive(userLiveUseCase){
            _userState.value = it
        }
    }

}