package com.example.solidpractice.service

import android.util.Log

class EmailNotification: NotificationService {
    override fun sendNotification(text: String) {
        Log.d("checkdata", "Notification: $text")
    }
}