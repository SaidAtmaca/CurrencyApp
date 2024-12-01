package com.saidatmaca.common

import android.os.Build
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object GlobalFunctions {
    fun getDeviceInfo(): String {

        val deviceModel = Build.MODEL
        val deviceBrand = Build.MANUFACTURER
        val deviceName = Build.DEVICE

        return "$deviceName $deviceModel $deviceBrand"

    }



    fun getDeviceCurrentTime() : String{
        return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Android API seviyesi 26 veya daha yüksekse, java.time.LocalDateTime kullan
            val currentDateTime = java.time.LocalDateTime.now()
            val formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            currentDateTime.format(formatter)
        } else {
            // Android API seviyesi 25 veya daha düşükse, java.util.Calendar kullan
            val calendar = Calendar.getInstance()
            //val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            dateFormat.format(calendar.time)
        }
    }

  //  fun getUserDefaultStartService(us)




}