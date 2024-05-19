package com.saidatmaca.currencyapp.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {


    @TypeConverter
    fun stringToList(value:String?) : List<String>?{
        return if (value == null){
            null
        }else{
            return Gson().fromJson(value, object : TypeToken<List<String>?>() {}.type)
        }
    }


    @TypeConverter
    fun listToString(list: List<String>?) : String?{
        return if (list == null){
            null
        }else{
            Gson().toJson(list)

        }
    }
}