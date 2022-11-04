package com.bence.wordlearner.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "group_id", defaultValue = "0") val groupId: Int,
    @ColumnInfo(name = "lang1", defaultValue = "") val lang1: String,
    @ColumnInfo(name = "lang2" , defaultValue = "") val lang2: String,
    @ColumnInfo(name = "priority", defaultValue = "0") val priority: Int,
    @ColumnInfo(name = "is_flagged", defaultValue = "0") val isFlagged: Boolean = false
)