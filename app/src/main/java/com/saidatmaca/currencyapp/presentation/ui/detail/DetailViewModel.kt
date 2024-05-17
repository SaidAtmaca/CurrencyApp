package com.saidatmaca.currencyapp.presentation.ui.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saidatmaca.currencyapp.core.common.enums.UIEvent
import com.saidatmaca.currencyapp.core.common.observeUserLive
import com.saidatmaca.currencyapp.data.local.entity.User
import com.saidatmaca.currencyapp.domain.model.Coin
import com.saidatmaca.currencyapp.domain.use_case.UserLiveUseCase
import com.saidatmaca.currencyapp.presentation.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val userLiveUseCase: UserLiveUseCase
) : ViewModel(){


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _userState : MutableState<User?> = mutableStateOf(null)
    val userState : State<User?> = _userState

    private val _coin : MutableState<Coin?> = mutableStateOf(null)
    val coin : State<Coin?> = _coin

    fun setCoin(coin: Coin?){
        _coin.value = coin
    }

    fun goToHomeScreen(){
        viewModelScope.launch {
            _eventFlow.emit(UIEvent.Navigate(Screen.HomeScreen.route))
            setCoin(null)
        }
    }


    init {
        this.observeUserLive(userLiveUseCase){
            _userState.value = it
        }
    }

}