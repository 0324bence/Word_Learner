package com.bence.wordlearner.componenets

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FullScreenDialog(onDismiss: ()->Unit, confirmButton: @Composable RowScope.()->Unit = {}, title: String = "", bottomBar: @Composable ()->Unit = {}, text: @Composable ()->Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = bottomBar,
        title = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Colse button"
                        )
                    }
                },
                actions = confirmButton
            )
        },
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.fillMaxSize().padding(0f.dp),
        text = text,
        shape = RectangleShape
    )
}