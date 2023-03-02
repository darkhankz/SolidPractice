package com.example.solidpractice.service

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG

class ToastNotification(private val context: Context): NotificationService {
    override fun sendNotification(text: String) {
        Toast.makeText(context, "Notification: $text", LENGTH_LONG).show()
    }
}