package com.bence.wordlearner

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.bence.wordlearner.componenets.bottomNav
import com.bence.wordlearner.database.WordDatabase
import com.bence.wordlearner.enums.LanguageToLearn
import com.bence.wordlearner.enums.SettingValues
import com.bence.wordlearner.ui.theme.WordLearnerTheme
import com.bence.wordlearner.views.Learn
import com.bence.wordlearner.views.Settings
import com.bence.wordlearner.views.WordList
import com.bence.wordlearner.database.Settings


fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

class MainActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val db = Room.databaseBuilder(LocalContext.current, WordDatabase::class.java, "word-learner").allowMainThreadQueries().build()

            MainApp(db)
            val activity = LocalContext.current.findActivity()
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(db: WordDatabase) {
    //#region Settings items
        val settingsDao = db.settingsDao()
        var settings = settingsDao.getSettings()
        if (settings == null) {
            settingsDao.insertNew(Settings())
            settings = settingsDao.getSettings()
        }
        var darkTheme by rememberSaveable { mutableStateOf(settings.darkTheme) }
        var lang1Label by rememberSaveable { mutableStateOf(settings.lang1Label) }
        var lang2Label by rememberSaveable { mutableStateOf(settings.lang2Label) }
        var langToLearn by rememberSaveable { mutableStateOf(settings.langToLearn) }
        var defaultPriority by rememberSaveable { mutableStateOf(settings.defaultPriority) }
    //#endregion
    WordLearnerTheme(darkTheme) {
        val navController = rememberNavController()

        Scaffold(
            bottomBar = { bottomNav(navController) },
            containerColor = MaterialTheme.colorScheme.surface
        ) { innerPadding ->
            //Navigation
            NavHost(
                navController = navController,
                startDestination = "learn_screen",
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                composable(route = "learn_screen") {
                    Box(modifier = Modifier
                        .fillMaxSize(),
                    ) {
                        Learn()
                    }
                }
                composable(route = "list_screen") {
                    Box(modifier = Modifier
                        .fillMaxSize(),
                    ) {
                        WordList(db, langToLearn = langToLearn)
                    }
                }
                composable(route = "settings_screen") {
                    Box(modifier = Modifier
                        .fillMaxSize(),
                    ) {
                        Settings(theme = darkTheme, lang1 = lang1Label, lang2 = lang2Label, langtolearn = langToLearn, defaultPriority = defaultPriority) { state, value ->
                            when (state) {
                                SettingValues.Theme -> {
                                    darkTheme = !darkTheme
                                    settingsDao.updateSettings(darkTheme = darkTheme)
                                }
                                SettingValues.Langs -> {
                                    lang1Label = value[0] as String
                                    lang2Label = value[1] as String
                                    settingsDao.updateSettings(lang1 = lang1Label, lang2 = lang2Label)
                                }
                                SettingValues.LangToLearn -> {
                                    langToLearn = if (value[0] as Boolean) LanguageToLearn.Lang2 else LanguageToLearn.Lang1
                                    settingsDao.updateSettings(langToLearn = langToLearn)
                                }
                                SettingValues.Priority -> {
                                    defaultPriority = value[0] as Int
                                    settingsDao.updateSettings(defaultPriority = defaultPriority)
                                }
                                //else -> {}
                            }
                        }
                    }
                }
            }
        }
    }
}


/*
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun DefaultPreview() {
    mainApp()
}*/
