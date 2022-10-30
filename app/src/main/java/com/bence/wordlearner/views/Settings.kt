package com.bence.wordlearner.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bence.wordlearner.enums.SettingValues
import com.bence.wordlearner.R
import com.bence.wordlearner.enums.LanguageToLearn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageModal(state: Boolean, lang1: String, lang2: String, onDismiss: ()->Unit, onConfirm: (val1:String, val2:String)->Unit) {
    if (state) {
        var lang1Label by remember { mutableStateOf(lang1) }
        var lang2Label by remember { mutableStateOf(lang2) }
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = { TextButton(onClick = { onConfirm(lang1Label, lang2Label) }) { Text(text = stringResource(id = R.string.settings_apply), color = MaterialTheme.colorScheme.primary) }},
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10f.dp)) {
                    OutlinedTextField(value = lang1Label, onValueChange = { lang1Label = it }, label = {
                        androidx.compose.material3.Text(
                            lang1
                        )
                    }, singleLine = true)
                    OutlinedTextField(value = lang2Label, onValueChange = { lang2Label = it }, label = {
                        androidx.compose.material3.Text(
                            lang2
                        )
                    }, singleLine = true)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityModal(state: Boolean, priority: Int, onDismiss: ()->Unit, onConfirm: (value: Int)->Unit) {
    if (state) {
        var priorityText by remember { mutableStateOf(priority.toString()) }
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = { TextButton(onClick = {
                val result = priorityText.toIntOrNull()
                if (result != null) onConfirm(result)
            }
        ) { Text(text = stringResource(id = R.string.settings_apply), color = MaterialTheme.colorScheme.primary) }},
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10f.dp)) {
                    OutlinedTextField(value = priorityText, onValueChange = { priorityText = it }, label = {
                        androidx.compose.material3.Text(
                            stringResource(id = R.string.settings_priority)
                        )
                    }, singleLine = true)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingItem(headline: @Composable ()->Unit, inside: @Composable ()->Unit) {
    ListItem(headlineText = headline, trailingContent = inside)
    Divider(color = MaterialTheme.colorScheme.secondary, thickness = 2f.dp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    CenterAlignedTopAppBar(
        title = {Text(text = stringResource(id = R.string.settings), color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.displaySmall)},
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2f.dp))
        //navigationIcon = { IconButton(onClick = { Back }) { Icon(Icons.Filled.ArrowBack, "Arrow back") }},
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Settings(theme: Boolean, lang1: String, lang2: String, langtolearn: LanguageToLearn, defaultPriority: Int, onSettingChange: (SettingValues:SettingValues, value: List<Any>)->Unit) {
    var langModal by remember { mutableStateOf(false) }
    var priorityModal by remember { mutableStateOf(false) }
    Scaffold(topBar = { TopBar() }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            Divider(color = MaterialTheme.colorScheme.secondary)
            SettingItem(
                headline = {Text(stringResource(id = R.string.settings_light_theme), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)}
            ) {
                Checkbox(
                    checked = !theme,
                    onCheckedChange = {
                        onSettingChange(SettingValues.Theme, listOf())
                    }
                )
            }
            SettingItem(
                headline = {Text(stringResource(id = R.string.settings_languages), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)}
            ) {
                IconButton(onClick = { langModal = true }) {
                    Icon(imageVector = Icons.Outlined.KeyboardArrowRight, contentDescription = "Languages Button")
                }
            }
            val langToLearnString = if (langtolearn == LanguageToLearn.Lang1) lang1 else lang2
            SettingItem(
                headline = {Text(stringResource(id = R.string.settings_langtolearn) + " " + langToLearnString, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)}
            ) {
                var checked by remember { mutableStateOf(langtolearn == LanguageToLearn.Lang2) }
                Switch(checked = checked, onCheckedChange = { value ->
                    onSettingChange(SettingValues.LangToLearn, listOf(value))
                    checked = value
                })
            }
            SettingItem(headline = { Text(text = stringResource(id = R.string.settings_default_priority), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface) }) {
                TextButton(onClick = { priorityModal = true }) {
                    Text(text = defaultPriority.toString(), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                }
            }
            Text(text = stringResource(id = R.string.copyright),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        LanguageModal(
            state = langModal,
            lang1 = lang1,
            lang2 = lang2,
            onDismiss = { langModal = false },
            onConfirm = { val1, val2 ->
                onSettingChange(SettingValues.Langs, listOf(val1, val2))
                langModal = false
            }
        )
        PriorityModal(
            state = priorityModal,
            priority = defaultPriority,
            onDismiss = { priorityModal = false },
            onConfirm = {value ->
                onSettingChange(SettingValues.Priority, listOf(value))
                priorityModal = false
            }
        )
    }

}