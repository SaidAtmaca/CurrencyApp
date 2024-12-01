package com.saidatmaca.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saidatmaca.domain.model.Coin
import com.saidatmaca.domain.model.CoinFavModel
import com.saidatmaca.domain.use_cases.CryptoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.abs

fun Float.formatChange(coin: Coin) : String{
    val decimalFormat = DecimalFormat("#,###.00")
    if (this >= 0){
        var plusValue = (coin.price * this) / 100
        val formattedPlusValue = decimalFormat.format(plusValue)
        val newString ="+$this%(+$$formattedPlusValue)"

        if (this == 0f){
            val zeroString ="$this%($$formattedPlusValue)"
            return zeroString
        }else{
            return newString
        }


    }else{
        var negativeValue = (coin.price * abs(this)) / 100
        val formattedNegativeValue = decimalFormat.format(negativeValue)
        val newString ="-$this%(-$$formattedNegativeValue)"

        return newString
    }



}
inline fun <reified T> String.toListByGson(): ArrayList<T> = if (isNotEmpty()) {
    Gson().fromJson(this, TypeToken.getParameterized(ArrayList::class.java, T::class.java).type)
} else {
    arrayListOf()
}

fun ViewModel.observeFavCoinList(
    cryptoUseCase: CryptoUseCase,
    updateCoinList: (coinList: List<CoinFavModel>) -> Unit
) {
    viewModelScope.launch {
        cryptoUseCase.coinFlow
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .collect { justCoinList ->

                updateCoinList(justCoinList)

            }
    }
}