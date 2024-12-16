package com.saidatmaca.model

import java.io.Serializable

data class Stats(
    var total: Int=0,
    var totalCoins: Int=0,
    var totalMarkets: Long=0,
    var totalExchanges: Long=0,
    var totalMarketCap: Double=0.0,
    var total24hVolume: Double=0.0
) : Serializable
