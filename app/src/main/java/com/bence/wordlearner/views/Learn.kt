package com.bence.wordlearner.views

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

@Composable
fun Learn() {

    var cardOffsetX by remember { mutableStateOf(0f)}
    var cardRotation by remember { mutableStateOf(0f)}
    var cardZIndex by remember { mutableStateOf(1f) }

    val cardOffsetXTransition by updateTransition(targetState = cardOffsetX, label = "").animateFloat(label = "") { it }
    val cardRotationTransition by updateTransition(targetState = cardRotation, label = "").animateFloat(label = "") { it }

    fun nextWord(know: Boolean) {

    }

   Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
       Box(
           modifier = Modifier
               .fillMaxHeight()
               .width(10f.dp)
               .background(
                   brush = Brush.horizontalGradient(
                       colors = listOf(
                           Color(0x66E52626),
                           Color.Transparent
                       )
                   )
               )
               .zIndex(5f)
       )
       Card(
           modifier = Modifier
               .fillMaxHeight(0.9f)
               .fillMaxWidth(0.8f)
               .zIndex(cardZIndex)
               .offset { IntOffset(cardOffsetXTransition.roundToInt(), 0) }
               .rotate(cardRotationTransition)
               .draggable(
                   orientation = Orientation.Horizontal,
                   state = rememberDraggableState {
                       cardOffsetX += it
                       cardRotation = if (it > 0f) 3f else -3f
                   },
                   onDragStopped = {
                       nextWord(cardOffsetX > 100f)
                       cardOffsetX = 0f
                       cardRotation = 0f
                   }
               ),

           colors = CardDefaults.cardColors(
               containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2f.dp),
               contentColor = MaterialTheme.colorScheme.onSurface
           ),
           elevation = CardDefaults.cardElevation(5f.dp)
       ) {
           Box(
               modifier = Modifier
                   .fillMaxSize()
                   .background(color = if (cardOffsetX > 100f) Color(0x266EE843) else if (cardOffsetX < -100f) Color(
                       0x26E52626
                   ) else Color.Transparent)
           ) {
               Text("X=${cardOffsetX}")
           }
       }
       Box(
           modifier = Modifier
               .fillMaxHeight()
               .width(10f.dp)
               .background(
                   brush = Brush.horizontalGradient(
                       colors = listOf(
                           Color.Transparent,
                           Color(0x666EE843)

                       )
                   )
               )
               .zIndex(5f)
       )
   }
}