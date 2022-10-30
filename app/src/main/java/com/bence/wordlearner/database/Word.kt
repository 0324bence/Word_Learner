package com.bence.wordlearner.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "group_id") val groupId: Int,
    @ColumnInfo(name = "lang1") val lang1: String,
    @ColumnInfo(name = "lang2") val lang2: String,
    @ColumnInfo(name = "priority") val priority: Int
)