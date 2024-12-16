package com.saidatmaca.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    private val context: Context,
    @Assisted workerParams: WorkerParameters,
) :  CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {


        showNotification(title = "Check Currencies",
            message = "check currency values")

        return Result.success()

    }

    private fun showNotification(title: String, message: String) {
        val channelId = "dummy_notification_channel"
        val notificationId = 1

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // NotificationChannel yalnızca API 26 ve üzeri cihazlarda gereklidir
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Dummy Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "This channel is used for dummy notifications."
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // Varsayılan bir simge kullanabilirsiniz
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(notificationId, notification)
    }

}