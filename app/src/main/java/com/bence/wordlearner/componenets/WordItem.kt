package com.bence.wordlearner.componenets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordItem(lang1: String, lang2: String, priority: String, onDelete: ()->Unit) {
    SwipableItem(
        headLine = {
                   Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                       Column() {
                           Text(text = lang1, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineMedium)
                       }
                       Column {
                           Text(text = "-", color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineMedium)
                       }
                       Column() {
                           Text(text = lang2, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineMedium)
                       }
                   }
        },
        trailingContent = { Text(text = priority) },
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