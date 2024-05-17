package com.saidatmaca.currencyapp.presentation.ui.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saidatmaca.currencyapp.core.common.GlobalValues
import com.saidatmaca.currencyapp.core.common.enums.UIEvent
import com.saidatmaca.currencyapp.core.common.observeUserLive
import com.saidatmaca.currencyapp.core.utils.Resource
import com.saidatmaca.currencyapp.data.local.entity.User
import com.saidatmaca.currencyapp.domain.model.ApiResponse
import com.saidatmaca.currencyapp.domain.model.Coin
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
class HomeViewModel @Inject constructor(
   private val userLiveUseCase: UserLiveUseCase,
   private val cryptoUseCase: CryptoUseCase,
) : ViewModel(){

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _userState : MutableState<User?> = mutableStateOf(null)
    val userState : State<User?> = _userState

    private val _apiResponse : MutableState<ApiResponse?> = mutableStateOf(null)
    val apiResponse : State<ApiResponse?> = _apiResponse

    private val _coins : MutableState<List<Coin>> = mutableStateOf(listOf())
    val coins : State<List<Coin>> = _coins


    private var job: Job? = null





    fun goToDetailScreen( coin: Coin){
        viewModelScope.launch {

            _eventFlow.emit(UIEvent.Navigate(Screen.DetailScreen.route))
        }
    }

    fun getAllCryptoData(){

        job = viewModelScope.launch {
            cryptoUseCase.getAllCryptoData()
                .onEach {result->
                    when (result) {

                        is Resource.Success -> {
                            GlobalValues.showLoading.postValue(false)
                            Log.e("allCryptoDataa1",result.data.toString())

                            result.data?.let {

                                _apiResponse.value = it
                                Log.e("allCryptoDataa3",it.status.toString())
                                Log.e("allCryptoDataa4",it.data.toString())

                                Log.e("allCryptoDataa2",it.data.coins.toString())
                                _coins.value=it.data.coins
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


   init {

       getAllCryptoData()

       this.observeUserLive(userLiveUseCase){
           _userState.value = it
       }
   }

}