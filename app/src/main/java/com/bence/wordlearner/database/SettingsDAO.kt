package com.bence.wordlearner.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.bence.wordlearner.enums.LanguageToLearn

@Dao
interface SettingsDAO {

    @Query("SELECT * FROM settings WHERE id = 1")
    fun getSettings(): Settings

    @Insert
    fun insertNew(settings: Settings)

    @Query("UPDATE settings SET darkTheme = :darkTheme WHERE id = 1")
    fun updateSettings(darkTheme: Boolean)

    @Query("UPDATE settings SET lang1_label = :lang1, lang2_label = :lang2 WHERE id = 1")
    fun updateSettings(lang1: String, lang2: String)

    @Query("UPDATE settings SET lang_to_learn = :langToLearn WHERE id = 1")
    fun updateSettings(langToLearn: LanguageToLearn)
}