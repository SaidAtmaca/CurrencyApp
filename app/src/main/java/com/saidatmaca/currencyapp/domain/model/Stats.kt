package com.saidatmaca.currencyapp.domain.model

import java.io.Serializable

data class Stats(
    val total: Int,
    val totalCoins: Int,
    val totalMarkets: Long,
    val totalExchanges: Long,
    val totalMarketCap: Double,
    val total24hVolume: Double
) : Serializable
