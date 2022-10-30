package com.bence.wordlearner.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bence.wordlearner.enums.LanguageToLearn

@Entity
data class Settings(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "darkTheme") val darkTheme: Boolean = false,
    @ColumnInfo(name = "lang1_label") val lang1Label: String = "Language 1",
    @ColumnInfo(name = "lang2_label") val lang2Label: String = "Language 2",
    @ColumnInfo(name = "lang_to_learn") val langToLearn: LanguageToLearn = LanguageToLearn.Lang2,
    @ColumnInfo(name = "default_priority", defaultValue = "50") val defaultPriority: Int = 50
)