package com.yellu.roomwordssample.database

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask
/**
Created by yellappa on 07,July,2019
 */
class WordRepository(application: Application) {
    var wordDao:WordDao
    var mAllWords:LiveData<List<Word>>

    init {
        val db:WordRoomDatabase = WordRoomDatabase.invoke(application)
        wordDao = db.wordDao()
        mAllWords = wordDao.getAllWords()
    }

    fun getAllWords():LiveData<List<Word>>{
        return mAllWords
    }

    fun insert(word:Word){
        InsertAsyncTask(wordDao, AppConstants.DBOperations.INSERT).execute(word)
    }

    fun deleteAll(){
        DeleteAllAsyncTask(wordDao).execute()
    }

    fun deleteWord(word:Word){
        InsertAsyncTask(wordDao, AppConstants.DBOperations.DELETE).execute(word)
    }

    fun updateWord(word: Word) {
        InsertAsyncTask(wordDao, AppConstants.DBOperations.UPDATE).execute(word)
    }

    //    ====================== insert/Update/Delete data async ===========
    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: WordDao, private val isInsert:AppConstants.DBOperations)
        :AsyncTask<Word, Void, Void>() {
        override fun doInBackground(vararg params: Word): Void? {
            when(isInsert){
                AppConstants.DBOperations.INSERT -> mAsyncTaskDao.insert(params[0])
                AppConstants.DBOperations.UPDATE -> mAsyncTaskDao.updateWord(params[0])
                AppConstants.DBOperations.DELETE -> mAsyncTaskDao.deleteWord(params[0])
            }
            return null
        }
    }

    //    ==================== delete All Words/Data async =================
    private class DeleteAllAsyncTask internal constructor(private val mAsyncTaskDao: WordDao)
        :AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }
    }
}