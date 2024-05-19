package com.saidatmaca.currencyapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saidatmaca.currencyapp.core.common.Constants
import java.io.Serializable

@Entity(tableName = Constants.COIN_TABLE)
data class CoinFavModel(
    @PrimaryKey
    var uuid: String = ""
):Serializable
