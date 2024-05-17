package com.saidatmaca.currencyapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.saidatmaca.currencyapp.core.common.Constants.ROOM_USER_TABLE
import java.io.Serializable

@Entity(tableName = ROOM_USER_TABLE)

data class User(
    @PrimaryKey
    @ColumnInfo("NameSurname"                ) var NameSurname               : String,
    @ColumnInfo("Age"                        ) var Age                       : String,
    @ColumnInfo("Weight"                     ) var Weight                    : String,
    @ColumnInfo("Height"                     ) var Height                    : String,
) : Serializable




















