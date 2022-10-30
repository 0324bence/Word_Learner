package com.bence.wordlearner.componenets

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NormalGroupItem(title: String, onClick: ()->Unit) {
    ListItem(
        headlineText = {Text(text = title, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineMedium)},
        trailingContent = {IconButton(onClick = onClick) { Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "more arrow") }}
    )
    Divider(color = MaterialTheme.colorScheme.secondary, thickness = 2f.dp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupItem(title: String, onClick: ()->Unit, onDelete: ()->Unit) {
    SwipableItem(
        headLine = { Text(text = title, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineMedium) },
        trailingContent = { IconButton(onClick = onClick) { Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "more arrow") } },
        actions = {
            IconButton(
                onClick = onDelete,
                modifier = it
                    .fillMaxHeight()
                    .aspectRatio(1f),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
                    contentColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete icon")
            }
        }
    )
    Divider(color = MaterialTheme.colorScheme.secondary, thickness = 2f.dp)
}