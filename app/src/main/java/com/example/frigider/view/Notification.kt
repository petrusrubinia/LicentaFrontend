package com.example.frigider.view

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class Notification :Application(){
    companion object{
        public const val CHANNEL_1:String = "channel1"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChaneel()
    }

    private fun createNotificationChaneel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            var channel: NotificationChannel = NotificationChannel(
                CHANNEL_1,
                "Channel_1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description ="This is my application "
            var manager:NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}