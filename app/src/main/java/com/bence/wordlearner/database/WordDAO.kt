package com.bence.wordlearner.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bence.wordlearner.enums.LanguageToLearn

@Dao
interface wordDAO {

    @Query("SELECT * FROM `group`")
    fun getGroups(): List<Group>

    @Query("SELECT * FROM `Group` WHERE id = :id")
    fun getGroup(id: Int): Group

    @Insert(entity = Group::class)
    fun addGroup(group: Group)

    @Insert(entity = Word::class)
    fun addWord(word: Word)

    @Insert(entity = Word::class)
    fun addWords(words: List<Word>)

    @Query(
        value = "SELECT * FROM word WHERE group_id = :groupId ORDER BY case :langToLearn when 0 then lang1 when 1 then lang2 end ASC"
    )
    fun getWords(groupId: Int, langToLearn: LanguageToLearn): List<Word>

    @Query("SELECT * FROM word ORDER BY case :langToLearn when 0 then lang1 when 1 then lang2 end ASC")
    fun getAllWords(langToLearn: LanguageToLearn): List<Word>

    @Delete(entity = Word::class)
    fun deleteWord(word: Word)
}