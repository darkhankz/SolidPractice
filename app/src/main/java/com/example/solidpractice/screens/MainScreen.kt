package com.example.solidpractice.screens

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.solidpractice.MainActivity
import com.example.solidpractice.listener.SwipeListiner
import com.example.solidpractice.logger.NotificationLogger
import com.example.solidpractice.service.EmailNotification
import com.example.solidpractice.service.ToastNotification
import com.example.solidpractice.service.UpdateService
import kotlin.math.roundToInt

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(
    context: Context,
    notificationType: MainActivity.NotificationType,
    updateService: UpdateService,
    notificationLogger: NotificationLogger,
    listiner: SwipeListiner
) {
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val width = 96.dp
    val squareSize = 48.dp
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to 0, sizePx to 1)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "The textfield has this text: ${updateService.updateText(textState.value.text)}",
//            modifier = Modifier.combinedClickable(
//                onClick = {},
//                onLongClick = {listiner.onLongClick()})
        )
        TextField(value = textState.value, onValueChange = { textState.value = it })
        Box(
            modifier = Modifier
                .width(width)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(0.3f) },
                    orientation = Orientation.Horizontal
                )
                .background(Color.LightGray)

        ) {
            when(swipeableState.currentValue){
                1 -> listiner.onRightSwipe()
                0 -> listiner.onLeftSwipe()

            }
            Box(
                modifier = Modifier
                    .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                    .size(squareSize)
                    .background(Color.DarkGray)

            ) {

            }
        }

        Button(
            onClick = {
                when (notificationType) {
                    MainActivity.NotificationType.TOAST -> {
                        ToastNotification(context).sendNotification(textState.value.text)
                        notificationLogger.logNotification(textState.value.text)
                    }
                    MainActivity.NotificationType.EMAIL -> {
                        EmailNotification().sendNotification(textState.value.text)
                        notificationLogger.logNotification(textState.value.text)
                    }
                }
            },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(text = "Send")

        }
    }
}