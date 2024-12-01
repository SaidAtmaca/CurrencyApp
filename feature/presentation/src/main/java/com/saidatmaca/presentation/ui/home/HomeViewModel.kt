package com.saidatmaca.presentation.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saidatmaca.common.GlobalValues
import com.saidatmaca.common.Resource
import com.saidatmaca.common.enums.SortValues
import com.saidatmaca.common.enums.UIEvent
import com.saidatmaca.domain.model.ApiResponse
import com.saidatmaca.domain.model.Coin
import com.saidatmaca.domain.model.CoinFavModel
import com.saidatmaca.domain.observeFavCoinList
import com.saidatmaca.domain.use_cases.CryptoUseCase
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
class HomeViewModel @Inject constructor(
    private val cryptoUseCase: CryptoUseCase,
) : ViewModel(){

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    private var job: Job? = null
    private val _apiResponse : MutableState<ApiResponse?> = mutableStateOf(null)

    val apiResponse : State<ApiResponse?> = _apiResponse
    private val _coins : MutableState<List<Coin>> = mutableStateOf(listOf())

    val coins : State<List<Coin>> = _coins
    private val _defaultCoins : MutableState<List<Coin>> = mutableStateOf(listOf())

    val defaultCoins : State<List<Coin>> = _defaultCoins
    private val _favList : MutableState<List<CoinFavModel>> = mutableStateOf(listOf())


    val favList : State<List<CoinFavModel>> = _favList



    fun sortCoinList(type : Int){

        when(type){
            SortValues.Default.value ->{
                _coins.value = _defaultCoins.value
            }
            SortValues.Price.value ->{
                _coins.value = _coins.value.sortedByDescending { it.price }
            }
            SortValues.MarketCap.value->{
                _coins.value = _coins.value.sortedByDescending { it.marketCap }
            }
            SortValues.Volume.value->{
                _coins.value = _coins.value.sortedByDescending { it.`24hVolume` }
            }
            SortValues.Change.value->{
                _coins.value = _coins.value.sortedByDescending { it.change }
            }
            SortValues.ListedAt.value->{
                _coins.value = _coins.value.sortedByDescending { it.listedAt }
            }
        }
    }


    fun goToDetailScreen( coin: Coin){
        viewModelScope.launch {

            _eventFlow.emit(UIEvent.Navigate(Screen.DetailScreen.route))
        }
    }

    fun checkFavCard(coin: Coin) : Boolean {
        val subList = _favList.value.filter { it.uuid == coin.uuid }

        return subList.isNotEmpty()
    }

    fun getAllCryptoData(){

        job = viewModelScope.launch {
            cryptoUseCase.getAllCryptoData()
                .onEach {result->
                    when (result) {

                        is Resource.Success -> {
                            GlobalValues.showLoading.postValue(false)

                            result.data?.let {

                                _apiResponse.value = it
                                _coins.value= listOf()
                                _coins.value=it.data.coins
                                _defaultCoins.value=it.data.coins
                            }



                        }

                        is Resource.Error -> {
                            GlobalValues.showLoading.postValue(false)
                            _eventFlow.emit(
                                UIEvent.ShowSnackbar(
                                    result.message ?: "Unknown error"
                                )
                            )
                        }

                        is Resource.Loading -> {
                            GlobalValues.showLoading.postValue(true)
                        }
                    }
                }.launchIn(this)
        }

    }


   init {

       getAllCryptoData()

       this.observeFavCoinList(cryptoUseCase){
           _favList.value = it

       }


   }

}