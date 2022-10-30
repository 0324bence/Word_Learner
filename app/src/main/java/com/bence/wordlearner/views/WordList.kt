package com.bence.wordlearner.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.bence.wordlearner.R
import com.bence.wordlearner.componenets.*
import com.bence.wordlearner.database.*
import com.bence.wordlearner.enums.LanguageToLearn

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
        },
        tonalElevation = 0f.dp
    )
}

@Composable
fun GroupList(list: List<Group>, onClick: (id: Int)->Unit, onDelete: (id: Int)->Unit) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
        item { Divider(color = MaterialTheme.colorScheme.secondary, thickness = 2f.dp) }
        item { NormalGroupItem(title = stringResource(id = R.string.word_list_all), onClick = {onClick(-3)}) }
        items(list, key = {it.id}) { item ->
            GroupItem(title = item.name, onClick = { onClick(item.id) }, onDelete = { onDelete(item.id) })
        }
    }
}

@Composable
fun WordsList(list: List<Word>, onDelete: (id: Int)->Unit) {
    val listState = rememberLazyListState()
    LazyColumn(state = listState) {
//        item { Divider(color = MaterialTheme.colorScheme.secondary, thickness = 2f.dp) }
        items(list) { item ->
            if (item.id > 0) {
                WordItem(lang1 = item.lang1, lang2 = item.lang2, priority = "[${item.priority}]", onDelete = { onDelete(item.id) })
            } else {
                NormalWordItem(lang1 = item.lang1, lang2 = item.lang2, priority = stringResource(id = R.string.word_list_priority))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordList(db: WordDatabase, langToLearn: LanguageToLearn) {
    var addSingleDialog by remember { mutableStateOf(false) }
    var groupDialog by remember { mutableStateOf(false) }
    var addGroupDialog by remember { mutableStateOf(false) }

    val settingsDAO = db.settingsDao()
    val settings = settingsDAO.getSettings()
    val wordDAO = db.wordDao()
    val lifecycleOwner = LocalLifecycleOwner.current
    val groups by remember {
        wordDAO.getGroups().flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }.collectAsState(initial = listOf())
    var currentGroup = 0
    Scaffold(
        topBar = { TopAppBar() },
        floatingActionButton = { FloatingActionButton(onClick = { addGroupDialog = true }) {
            Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add icon")
        }}
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
                GroupList(
                    list = groups,
                    onClick = {group -> groupDialog = true; currentGroup = group},
                    onDelete = { id ->
                        //groups.removeIf { group -> group.id == id }
                        wordDAO.deleteGroupAndWords(id)
                    }
                )



            //Add new group
            if (addGroupDialog) {
                var groupName by remember { mutableStateOf("") }
                AlertDialog(
                    onDismissRequest = { addGroupDialog = false },
                    confirmButton = { TextButton(onClick = {
                        val newGroup = Group(name = groupName)
                        //groups.add(newGroup)
                        wordDAO.addGroup(newGroup)
                        addGroupDialog = false
                    }) {
                        Text("Add")
                    }},
                    title = { Text(text = stringResource(id = R.string.word_list_new_group)) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(10f.dp)) {
                            OutlinedTextField(value = groupName, onValueChange = { str -> groupName = str }, label = { Text(
                                stringResource(id = R.string.word_list_group)) }, singleLine = true)
                        }
                    }
                )
            }

            // Group content dialog
            if (groupDialog) {
                val head = remember {
                    mutableStateListOf(
                        Word(-1, -1, settings.lang1Label, settings.lang2Label, -1)
                    )
                }

                val localLifecycleOwner = LocalLifecycleOwner.current
                val wordList by if (currentGroup < 0)
                    remember {
                        wordDAO.getAllWords(settings.langToLearn)
                            .flowWithLifecycle(localLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                    }.collectAsState(initial = listOf())
                else
                    remember {
                        wordDAO.getWords(currentGroup, settings.langToLearn)
                            .flowWithLifecycle(localLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                    }.collectAsState(initial = listOf())


                FullScreenDialog(
                    onDismiss = { groupDialog = false },
                    title = "Title",
                    bottomBar = { if (currentGroup >= 0) BottomAppBar(FabClick = { /*TODO*/ }, SingleClick = { addSingleDialog = true }, ListClick = {}) }
                ) {
                    Scaffold() { padding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding)
                        ) {
                            WordsList(
                                list = head + wordList,
                                onDelete = { id ->
                                    //wordList.removeIf { word -> word.id == id }
                                    wordDAO.deleteWord(partialWord(id))
                                }
                            )
                        }
                    }
                }

                // Add Single word dialog
                if (addSingleDialog) {
                    var lang1 by remember { mutableStateOf("") }
                    var lang2 by remember { mutableStateOf("") }
                    AlertDialog(
                        onDismissRequest = { addSingleDialog = false },
                        confirmButton = { TextButton(onClick = {
                            if (currentGroup >= 0) {
                                val newWord = Word(lang1 = lang1, lang2 = lang2, priority = settings.defaultPriority, groupId = currentGroup)
                                //wordList.add(newWord)
                                db.wordDao().addWord(newWord)
                                addSingleDialog = false
                            }
                        }) {
                            Text("Add")
                        }},
                        icon = { Icon(painter = painterResource(id = R.drawable.plus1_icon), contentDescription = "Plus1 button") },
                        text = {
                            Column(verticalArrangement = Arrangement.spacedBy(10f.dp)) {
                                OutlinedTextField(value = lang1, onValueChange = { str -> lang1 = str }, label = { Text(settings.lang1Label) }, singleLine = true)
                                OutlinedTextField(value = lang2, onValueChange = { str -> lang2 = str }, label = { Text(settings.lang2Label) }, singleLine = true)
                            }
                        }
                    )
                }
            }
        }
    }
}