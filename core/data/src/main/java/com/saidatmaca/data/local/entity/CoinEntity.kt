package com.saidatmaca.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class CoinEntity(
    @PrimaryKey var uuid: String = "",
    var symbol: String = "",
    var name: String = "",
    var color: String? = null,
    var iconUrl: String = "",
    var marketCap: Long = 0L,
    var price: Double= 0.0,
    var listedAt: Long= -1L,
    var tier: Int = 0,
    var change: Float = 0f,
    var rank: Int = 0,
    var sparkline: List<Double?> = listOf(),
    var lowVolume: Boolean = false,
    var coinrankingUrl: String = "",
    var `24hVolume`: Double = 0.0,
    var btcPrice: Double = 0.0,
    var contractAddresses: List<String> = listOf()
):Serializable
