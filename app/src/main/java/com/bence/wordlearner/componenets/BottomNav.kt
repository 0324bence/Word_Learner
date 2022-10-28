package com.bence.wordlearner.componenets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bence.wordlearner.R

@Composable
fun bottomNav(navController: NavHostController) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route
    NavigationBar(containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2f.dp), modifier = Modifier.zIndex(1f)) {
        NavigationBarItem(
            selected = currentRoute == "learn_screen",
            icon = { Icon(painterResource(id = R.drawable.language_icon), contentDescription = "") },
            onClick = { navController.navigate("learn_screen") },
//            label = {Text(stringResource(R.string.learn))}
            label = { Text(stringResource(R.string.learn)) }
        )
        NavigationBarItem(
            selected = currentRoute == "list_screen",
            icon = { Icon(painterResource(id = R.drawable.folder_icon), contentDescription = "") },
            onClick = { navController.navigate("list_screen") },
            label = { Text(stringResource(R.string.word_list)) }
        )
        NavigationBarItem(
            selected = currentRoute == "settings_screen",
            icon = { Icon(Icons.Outlined.Settings, contentDescription = "") },
            onClick = { navController.navigate("settings_screen") },
            label = { Text(stringResource(R.string.settings)) }
        )
    }
}