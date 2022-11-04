package com.bence.wordlearner.database

import androidx.room.*
import com.bence.wordlearner.enums.LanguageToLearn
import kotlinx.coroutines.flow.Flow

@Dao
interface wordDAO {

    @Query("SELECT * FROM `group` ORDER BY name ASC")
    fun getGroups(): Flow<List<Group>>

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
    fun getWords(groupId: Int, langToLearn: LanguageToLearn): Flow<List<Word>>

    @Query("SELECT * FROM word ORDER BY case :langToLearn when 0 then lang1 when 1 then lang2 end ASC")
    fun getAllWords(langToLearn: LanguageToLearn): Flow<List<Word>>

    @Query("SELECT * FROM (SELECT * FROM word WHERE group_id = :groupId ORDER BY priority DESC LIMIT 3) ORDER BY RANDOM() LIMIT 1")
    fun getWordByPriority(groupId: Int): Flow<Word>

    //@Query("SELECT * FROM (SELECT * FROM word ORDER BY priority DESC LIMIT 3) ORDER BY RANDOM() LIMIT 1")
    @Query("SELECT * FROM (SELECT * FROM word ORDER BY priority DESC LIMIT 3) ORDER BY RANDOM() LIMIT 1")
    fun getAllWordByPriority(): Flow<Word>

    @Query("UPDATE word SET priority = (priority - :amount) WHERE id = :wordId")
    fun updateWordPriority(wordId: Int, amount: Int)

    @Query("UPDATE word SET is_flagged = :value WHERE id = :wordId")
    fun flagWord(wordId: Int, value: Boolean)

    @Query("SELECT * FROM word WHERE is_flagged = 1")
    fun getFlaggedWords(): List<Word>

    @Delete(entity = Word::class)
    fun deleteWord(wordId: partialWord)

    @Delete(entity = Group::class)
    fun deleteGroup(groupId: partialGroup)

    @Query("DELETE FROM word WHERE group_id = :groupId")
    fun deleteGroupWords(groupId: partialGroup)

    @Transaction
    fun deleteGroupAndWords(groupId: Int) {
        deleteGroupWords(partialGroup(groupId))
        deleteGroup(partialGroup(groupId))
    }
}

data class partialWord(val id: Int)
data class  partialGroup(val id: Int)