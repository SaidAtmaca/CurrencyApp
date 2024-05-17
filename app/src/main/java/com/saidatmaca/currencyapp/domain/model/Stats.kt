package com.saidatmaca.currencyapp.domain.model

import java.io.Serializable

data class Stats(
    val total: Int,
    val totalCoins: Int,
    val totalMarkets: Int,
    val totalExchanges: Int,
    val totalMarketCap: String,
    val total24hVolume: String
) : Serializable
