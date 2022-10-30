package com.bence.wordlearner.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

//@Database(entities = [Settings::class], version = 1, exportSchema = true)
//abstract class WordDatabase : RoomDatabase() {
//    abstract fun settingsDao(): SettingsDAO
//}

//@Database(entities = [Settings::class, Group::class], version = 2, autoMigrations = [AutoMigration(from = 1, to = 2)], exportSchema = true)
//abstract class WordDatabase : RoomDatabase() {
//    abstract fun settingsDao(): SettingsDAO
//    abstract fun wordDao(): wordDAO
//}

@Database(entities = [Settings::class, Group::class, Word::class], version = 4, autoMigrations = [AutoMigration(from = 3, to = 4)], exportSchema = true)
abstract class WordDatabase : RoomDatabase() {
    abstract fun settingsDao(): SettingsDAO
    abstract fun wordDao(): wordDAO
}