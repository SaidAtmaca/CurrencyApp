package com.saidatmaca.currencyapp.domain.model

import java.io.Serializable

data class Coin(

    var uuid: String = "",
    var symbol: String = "",
    var name: String = "",
    var color: String? = null,
    var iconUrl: String = "",
    var marketCap: String = "",
    var price: String= "",
    var listedAt: Long= -1L,
    var tier: Int = 0,
    var change: String = "",
    var rank: Int = 0,
    var sparkline: List<String?> = listOf(),
    var lowVolume: Boolean = false,
    var coinrankingUrl: String = "",
    var `24hVolume`: String = "",
    var btcPrice: String = "",
    var contractAddresses: List<String> = listOf()


):Serializable

