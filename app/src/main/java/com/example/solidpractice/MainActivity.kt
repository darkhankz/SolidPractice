package com.example.solidpractice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.solidpractice.listener.SwipeListiner
import com.example.solidpractice.logger.EmailNotificationLogger
import com.example.solidpractice.logger.ToastNotificationLogger
import com.example.solidpractice.screens.MainScreen
import com.example.solidpractice.service.MySQLDatabase
import com.example.solidpractice.service.UpdateService
import com.example.solidpractice.ui.theme.SolidPracticeTheme

class MainActivity : ComponentActivity(), SwipeListiner {
    enum class NotificationType {
        TOAST,
        EMAIL
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val notificationType = NotificationType.TOAST
        val updateService = UpdateService()
        val notificationLogger = when (notificationType) {
            NotificationType.TOAST -> ToastNotificationLogger()
            NotificationType.EMAIL -> EmailNotificationLogger(MySQLDatabase())
        }
        setContent {
            SolidPracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(
                        context = applicationContext,
                        notificationType = notificationType,
                        updateService = updateService,
                        notificationLogger = notificationLogger,
                        listiner = this
                    )
                }
            }
        }
    }

    override fun onRightSwipe() {
        Log.d("checkdata", "onRightSwipe")
    }

    override fun onLeftSwipe() {
        Log.d("checkdata", "onLeftSwipe")

    }

}

