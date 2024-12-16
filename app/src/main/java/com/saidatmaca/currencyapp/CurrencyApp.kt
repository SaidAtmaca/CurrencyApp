package com.saidatmaca.currencyapp

import android.app.Application
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.saidatmaca.worker.NotificationWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class CurrencyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        try {
            val periodicWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
                15, // Minimum 15 dakika
                TimeUnit.MINUTES
            ).build()

            WorkManager.getInstance(applicationContext).enqueue(periodicWorkRequest)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}