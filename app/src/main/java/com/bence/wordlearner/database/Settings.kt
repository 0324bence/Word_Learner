package com.bence.wordlearner.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bence.wordlearner.enums.LanguageToLearn

@Entity
data class Settings(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "darkTheme") val darkTheme: Boolean,
    @ColumnInfo(name = "lang1_label") val lang1Label: String,
    @ColumnInfo(name = "lang2_label") val lang2Label: String,
    @ColumnInfo(name = "lang_to_learn") val langToLearn: LanguageToLearn
)