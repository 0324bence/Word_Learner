package com.bence.wordlearner.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Settings::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    abstract fun settingsDao(): SettingsDAO
}