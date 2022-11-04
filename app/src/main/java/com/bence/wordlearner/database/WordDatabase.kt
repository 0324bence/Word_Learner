package com.bence.wordlearner.database

import androidx.room.*

//@Database(entities = [Settings::class], version = 1, exportSchema = true)
//abstract class WordDatabase : RoomDatabase() {
//    abstract fun settingsDao(): SettingsDAO
//}

//@Database(entities = [Settings::class, Group::class], version = 2, autoMigrations = [AutoMigration(from = 1, to = 2)], exportSchema = true)
//abstract class WordDatabase : RoomDatabase() {
//    abstract fun settingsDao(): SettingsDAO
//    abstract fun wordDao(): wordDAO
//}

@Database(entities = [Settings::class, Group::class, Word::class], version = 8, autoMigrations = [AutoMigration(from = 7, to = 8)], exportSchema = true)
@TypeConverters(Converters::class)
abstract class WordDatabase : RoomDatabase() {
    abstract fun settingsDao(): SettingsDAO
    abstract fun wordDao(): wordDAO
}

class Converters {
    @TypeConverter
    fun fromPartioalGroup(value: partialGroup): Int {
        return value.id
    }

    @TypeConverter
    fun fromPartioalWord(value: partialWord): Int {
        return value.id
    }
}