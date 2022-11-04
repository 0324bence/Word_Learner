package com.bence.wordlearner.views

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bence.wordlearner.R
import com.bence.wordlearner.database.Word
import com.bence.wordlearner.database.WordDatabase
import com.bence.wordlearner.enums.LanguageToLearn
import kotlin.math.roundToInt

@Composable
fun Learn(db: WordDatabase, group: Int) {

    var cardOffsetX by remember { mutableStateOf(0f)}
    var cardRotation by remember { mutableStateOf(0f)}
    val cardZIndex by remember { mutableStateOf(1f) }

    val cardOffsetXTransition by updateTransition(targetState = cardOffsetX, label = "").animateFloat(label = "") { it }
    val cardRotationTransition by updateTransition(targetState = cardRotation, label = "").animateFloat(label = "") { it }

    val settingsDAO = db.settingsDao()
    val settings = settingsDAO.getSettings()

    val wordDao = db.wordDao()
    val currentWord by when (group) {
        -1 -> remember {
            wordDao.getAllWordByPriority()
        }
        else -> remember {
            wordDao.getWordByPriority(groupId = group)
        }
    }.collectAsState(initial = Word(0, 1,"a","a",10 ))

    var wordRevealed by remember { mutableStateOf(false) }
    var currentLearnerWord = ""

    fun nextWord(know: Boolean) {
        println("Know: $know")
        if (know) {
            wordDao.updateWordPriority(currentWord.id, 10)
        } else {
            wordDao.updateWordPriority(currentWord.id, 10 * -1)
        }
        cardOffsetX = 0f
        cardRotation = 0f
        wordRevealed = false
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
       if (currentWord != null) {
           currentLearnerWord = if (settings.langToLearn == LanguageToLearn.Lang1) currentWord.lang1 else currentWord.lang2
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
                           if (cardOffsetX > 100f) nextWord(true) else if (cardOffsetX < -100f) nextWord(false)
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
                       .background(
                           color = when {
                               (cardOffsetX > 100f) -> Color(0xD6EE843); (cardOffsetX < -100f) -> Color(0xDE52626)
                               else -> Color.Transparent
                           }
                       )
                       .padding(10f.dp)
               ) {
                   Column(modifier = Modifier.fillMaxSize()) {
                       Column(modifier = Modifier.weight(weight = 9f, fill = true), verticalArrangement = Arrangement.spacedBy(5f.dp)) {
                           Column(modifier = Modifier
                               .fillMaxWidth()
                               .weight(weight = 3f, fill = true), verticalArrangement = Arrangement.Center) {
                               Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                   Text("[${currentWord.priority}]", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onSurface)
                               }
                               Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                   Text(
                                       if (settings.langToLearn == LanguageToLearn.Lang1) currentWord.lang2 else currentWord.lang1,
                                       style = MaterialTheme.typography.headlineLarge,
                                       color = MaterialTheme.colorScheme.onSurface,
                                       textAlign = TextAlign.Center
                                   )
                               }
                           }
                           Column(modifier = Modifier
                               .fillMaxWidth()
                               .weight(weight = 1f, fill = true)) {
                               Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
                                   Box(modifier = Modifier
                                       .fillMaxHeight()
                                       .width(2f.dp)
                                       .background(color = MaterialTheme.colorScheme.secondary)) {}
                               }

                           }
                           Column(modifier = Modifier
                               .fillMaxWidth()
                               .weight(weight = 3f, fill = true), verticalArrangement = Arrangement.Center) {
                               Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                                   Text(text = if (wordRevealed) currentLearnerWord else "***", style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onSurface, textAlign = TextAlign.Center)
                               }
                           }
                       }
                       Column(modifier = Modifier.weight(weight = 3f, fill = true), verticalArrangement = Arrangement.SpaceBetween) {
                           Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                               Button(
                                   onClick = { wordRevealed = true },
                                   colors = ButtonDefaults.buttonColors(
                                       containerColor = MaterialTheme.colorScheme.surface,
                                       contentColor = MaterialTheme.colorScheme.primary
                                   ),
                                   elevation = ButtonDefaults.elevatedButtonElevation()
                               ) {
                                   Text(text = stringResource(id = R.string.learn_show))
                               }
                           }
                           Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                               //Dont knowFAB
                               FloatingActionButton(
                                   onClick = {
                                       nextWord(false)
                                   },
                                   containerColor = Color(0xFF633B48),
                                   contentColor = MaterialTheme.colorScheme.tertiary
                               ) {
                                   Icon(imageVector = Icons.Outlined.Close, contentDescription = "Delete fab button")
                               }
                               IconButton(onClick = { /*TODO*/ }) {
                                   Icon(painter = painterResource(id = R.drawable.flag_icon), contentDescription = "Flag icon")
                               }
                               FloatingActionButton(onClick = { nextWord(true) }, containerColor = Color(0xFF43633B), contentColor = MaterialTheme.colorScheme.tertiary) {
                                   Icon(imageVector = Icons.Outlined.Check, contentDescription = "Delete fab button")
                               }
                           }
                       }
                   }
               }
           }
       } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(stringResource(id = R.string.learn_no_words), color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineLarge)
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