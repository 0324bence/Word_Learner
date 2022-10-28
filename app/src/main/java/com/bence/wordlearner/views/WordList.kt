package com.bence.wordlearner.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bence.wordlearner.R
import com.bence.wordlearner.componenets.GroupItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.word_list_groups), color = MaterialTheme.colorScheme.onSurface, style = MaterialTheme.typography.displaySmall)
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2f.dp))
    )
}

@Composable
fun BottomAppBar(FabClick:()->Unit, SingleClick:()->Unit, ListClick:()->Unit) {
    BottomAppBar(
        floatingActionButton = {
            FloatingActionButton(onClick = FabClick) {
                Icon(painter = painterResource(id = R.drawable.camera_icon), contentDescription = "Camera add FAB")
            }
        },
        actions = {
            IconButton(onClick = SingleClick) {
                Icon(painter = painterResource(id = R.drawable.plus1_icon), contentDescription = "Plus1 button")
            }
            IconButton(onClick = ListClick) {
                Icon(painter = painterResource(id = R.drawable.addlist_icon), contentDescription = "Addmore button")
            }
        }
    )
}

@Composable
fun List() {
    val itemList by remember { mutableStateOf(mutableStateListOf(1)+(2..30)) }
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        item { Divider(color = MaterialTheme.colorScheme.secondary, thickness = 2f.dp) }
        items(itemList) { item ->
            GroupItem(title = item.toString(), onClick = {/**/})
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordList() {
    var addSingleDialog by remember { mutableStateOf(false) }
    Scaffold(
        topBar = { TopAppBar() },
        bottomBar = {
            BottomAppBar(FabClick = { /*TODO*/ }, SingleClick = { addSingleDialog = true }, ListClick = {})
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
                List()
            // Add Single dialog
            if (addSingleDialog) {
                var lang1 by remember { mutableStateOf("") }
                var lang2 by remember { mutableStateOf("") }
                AlertDialog(
                    onDismissRequest = { addSingleDialog = false },
                    confirmButton = { TextButton(onClick = { addSingleDialog = false }) {
                        Text("Add")
                    }},
                    icon = { Icon(painter = painterResource(id = R.drawable.plus1_icon), contentDescription = "Plus1 button") },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(10f.dp)) {
                            OutlinedTextField(value = lang1, onValueChange = { str -> lang1 = str }, label = { Text("Language1") }, singleLine = true)
                            OutlinedTextField(value = lang2, onValueChange = { str -> lang2 = str }, label = { Text("Language2") }, singleLine = true)
                        }
                    }
                )
            }
        }
    }
}