package com.bence.wordlearner.componenets

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable

@Composable
fun NormalDialog(onDismiss: () -> Unit, confirmButton: @Composable() () -> Unit, icon: @Composable ()->Unit = {}, title: @Composable ()->Unit = {}, text: @Composable () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = confirmButton,
        icon = icon,
        title = title,
        text = text
    )
}