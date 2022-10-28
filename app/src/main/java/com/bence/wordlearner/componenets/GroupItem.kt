package com.bence.wordlearner.componenets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupItem(title: String, onClick: ()->Unit) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineText = {Text(text = title, color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.headlineMedium)},
        trailingContent = {Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = "more arrow")}
    )
    Divider(color = MaterialTheme.colorScheme.secondary, thickness = 2f.dp)
}