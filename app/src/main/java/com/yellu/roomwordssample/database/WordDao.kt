package com.yellu.roomwordssample.database

import androidx.lifecycle.LiveData
import androidx.room.*
/**
Created by yellappa on 07,July,2019
 */
@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(mWord:Word)

    @Query("DELETE FROM word_table")
    fun deleteAll()

    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAllWords():LiveData<List<Word>>

    @Query("SELECT * from word_table LIMIT 1")
    fun getAnyWord():Array<Word>

    @Delete
    fun deleteWord(word:Word)

    @Update
    fun updateWord(word: Word)
}