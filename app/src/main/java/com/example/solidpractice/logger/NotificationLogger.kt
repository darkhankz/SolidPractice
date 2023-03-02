package com.example.solidpractice.logger

import android.util.Log
import com.example.solidpractice.service.Database

open class NotificationLogger {
    open fun logNotification(msg: String){
        Log.d("checkdata", "NotificationLogger: logNotification: $msg")

    }
}

class EmailNotificationLogger(private val database: Database): NotificationLogger() {
    override fun logNotification(msg: String) {
        database.save(msg)
        Log.d("checkdata", "EmailNotificationLogger: logNotification: $msg")
    }

}

class ToastNotificationLogger: NotificationLogger() {
    override fun logNotification(msg: String) {
        Log.d("checkdata", "ToastNotificationLogger: logNotification: $msg")
    }

}