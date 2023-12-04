package com.example.myapplication
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 创建一个可滑动的小球
            var counter by remember { mutableStateOf("") }

            Column(Modifier.fillMaxSize()) {
                Box(modifier =
                Modifier.fillMaxSize()
                    .weight(1f)
                ) {
                    var offset by remember { mutableStateOf(IntOffset.Zero) }
                    Box(
                        modifier = Modifier
                            .offset { offset }
                            .pointerInput(Unit) {
                                detectDragGestures { change, dragAmount ->
                                    change.consumePositionChange()
                                    offset += dragAmount.round()
                                    counter +=  updateDragDirection(dragAmount)+"\n"
                                }
                            }
                            .size(100.dp)
                            .background(color = Color.Red, shape = CircleShape)
                    )
                }
                // 下部分
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.White)
                ) {
                    // 滑动方向
                    Text(
                        text = "$counter",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }

    }
}

@Composable
fun DraggableBall() {

}


 fun updateDragDirection(dragAmount: Offset):String {
    val horizontalThreshold = 10.dp
    val verticalThreshold = 10.dp

    val direction = when {
        dragAmount.x.dp > horizontalThreshold -> "you swiped right"
        dragAmount.x.dp < -horizontalThreshold -> "you swiped left"
        dragAmount.y.dp > verticalThreshold -> "you swiped down"
        dragAmount.y.dp < -verticalThreshold -> "you swiped up"
        else -> ""
    }
     return direction;
}