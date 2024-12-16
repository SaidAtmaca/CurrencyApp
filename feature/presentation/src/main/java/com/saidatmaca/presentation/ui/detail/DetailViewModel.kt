package com.saidatmaca.presentation.ui.detail

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jaikeerthick.composable_graphs.composables.line.model.LineData
import com.saidatmaca.common.GlobalValues
import com.saidatmaca.common.Resource
import com.saidatmaca.common.enums.UIEvent
import com.saidatmaca.common.toFormattedDate
import com.saidatmaca.core.viewmodel.BaseViewModel
import com.saidatmaca.domain.observeFavCoinList
import com.saidatmaca.domain.use_cases.CryptoUseCase
import com.saidatmaca.model.Coin
import com.saidatmaca.model.CoinFavModel
import com.saidatmaca.model.HistoryApiResponse
import com.saidatmaca.model.HistoryModel
import com.saidatmaca.presentation.util.Screen
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
    private val cryptoUseCase: CryptoUseCase,
) : BaseViewModel() {


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private val _coin: MutableState<Coin?> = mutableStateOf(null)
    val coin: State<Coin?> = _coin

    private val _historyApiResponse: MutableState<HistoryApiResponse?> = mutableStateOf(null)
    val historyApiResponse: State<HistoryApiResponse?> = _historyApiResponse

    private val _historicalPriceList: MutableState<List<HistoryModel>> = mutableStateOf(listOf())
    val historicalPriceList: State<List<HistoryModel>> = _historicalPriceList

    private val _isFavCoin: MutableState<Boolean> = mutableStateOf(false)
    val isFavCoin: State<Boolean> = _isFavCoin

    private val _favList: MutableState<List<CoinFavModel>> = mutableStateOf(listOf())
    val favList: State<List<CoinFavModel>> = _favList

    private val _highestPrice: MutableState<Double> = mutableStateOf(0.0)
    val highestPrice: State<Double> = _highestPrice

    private val _lowestPrice: MutableState<Double> = mutableStateOf(0.0)
    val lowestPrice: State<Double> = _lowestPrice


    fun setFavCoin(boolean: Boolean) {
        _isFavCoin.value = boolean
    }


    val lineGraphData: SnapshotStateList<LineData> = mutableStateListOf()


    //private var job: Job? = null

    fun setCoin(coin: Coin?) {
        _coin.value = coin
    }

    fun goToHomeScreen() {
        viewModelScope.launch {
            _eventFlow.emit(UIEvent.Navigate(Screen.HomeScreen.route))
            setCoin(null)
        }
    }

    fun getCryptoHistoricalData(coinId: String) {

        job = viewModelScope.launch {
            cryptoUseCase.getCryptoPriceHistory(coinId)
                .onEach { result ->
                    when (result) {

                        is Resource.Success -> {
                            GlobalValues.showLoading.postValue(false)
                            Log.e("allCryptoDataa1", result.data.toString())

                            result.data?.let {

                                _historyApiResponse.value = it
                                _historicalPriceList.value = it.data.history
                                createGraphUI()
                                findHighAndLowPrice()


                            }


                        }

                        is Resource.Error -> {
                            GlobalValues.showLoading.postValue(false)
                            Log.e("allCryptoDataa2", result.data.toString())
                            _eventFlow.emit(
                                UIEvent.ShowSnackbar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }

                        is Resource.Loading -> {
                            Log.e("allCryptoDataa3", result.data.toString())
                            GlobalValues.showLoading.postValue(true)
                        }
                    }
                }.launchIn(this)
        }

    }

    private fun findHighAndLowPrice() {

        val priceList = arrayListOf<Double>()
        _historicalPriceList.value.forEach {
            priceList.add(it.price)
        }

        val maxValue = priceList.max()
        val minValue = priceList.min()

        _highestPrice.value = maxValue
        _lowestPrice.value = minValue

    }

    private fun createGraphUI() {

        val data = arrayListOf<LineData>()
        val size = _historicalPriceList.value.size


        data.add(
            LineData(
                _historicalPriceList.value.last().timestamp.toFormattedDate(),
                _historicalPriceList.value.last().price
            )
        )
        data.add(
            LineData(
                _historicalPriceList.value.get(size / 2).timestamp.toFormattedDate(),
                _historicalPriceList.value.get(size / 2).price
            )
        )
        data.add(
            LineData(
                _historicalPriceList.value.get(size / 4).timestamp.toFormattedDate(),
                _historicalPriceList.value.get(size / 4).price
            )
        )
        data.add(
            LineData(
                _historicalPriceList.value.get(0).timestamp.toFormattedDate(),
                _historicalPriceList.value.get(3).price
            )
        )

        lineGraphData.clear()
        lineGraphData.addAll(data)


    }


    fun checkFavPosition() {
        val subList = _favList.value.filter { it.uuid == _coin.value?.uuid }
        _isFavCoin.value = subList.isNotEmpty()

    }

    fun favClicked() {

        if (_isFavCoin.value) {
            removeFromFavList()
        } else {
            addCoinFavList()
        }

    }

    fun addCoinFavList() {
        _coin.value?.let {
            val newCoin = CoinFavModel(it.uuid)
            var tempArrayList = arrayListOf<CoinFavModel>()
            tempArrayList.addAll(_favList.value)
            tempArrayList.add(newCoin)


            viewModelScope.launch {

                cryptoUseCase.insertCoinList(tempArrayList)

            }

        }

    }

    fun removeFromFavList() {
        _coin.value?.let { coin ->

            val tempArrayList = arrayListOf<CoinFavModel>()
            tempArrayList.addAll(_favList.value)
            tempArrayList.removeIf { it.uuid == coin.uuid }

            viewModelScope.launch {

                cryptoUseCase.insertCoinList(tempArrayList)

            }


        }
    }


    init {

        this.observeFavCoinList(cryptoUseCase) {
            Log.e("favCoinList", it.toString())
            _favList.value = it
            checkFavPosition()
        }
    }

}