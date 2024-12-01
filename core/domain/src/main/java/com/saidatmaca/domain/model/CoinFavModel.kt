package com.saidatmaca.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saidatmaca.common.Constants
import java.io.Serializable
@Entity(tableName = Constants.COIN_TABLE)
data class CoinFavModel(
    @PrimaryKey
    var uuid: String = ""
):Serializable
