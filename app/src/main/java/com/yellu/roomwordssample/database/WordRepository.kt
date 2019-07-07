package com.yellu.roomwordssample.database

import android.app.Application
import androidx.lifecycle.LiveData
import android.os.AsyncTask

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
        InsertAsyncTask(wordDao, true).execute(word)
    }

    fun deleteAll(){
        DeleteAsyncTask(wordDao).execute()
    }

    fun deleteWord(word:Word){
        InsertAsyncTask(wordDao, false).execute(word)
    }

    fun updateWord(word: Word) {
        UpdateWordAsyncTask(wordDao).execute(word)
    }

    //    ====================== insert data async ===========
    private class InsertAsyncTask internal constructor(private val mAsyncTaskDao: WordDao, private val isInsert:Boolean) : AsyncTask<Word, Void, Void>() {

        override fun doInBackground(vararg params: Word): Void? {
            if(isInsert){
                mAsyncTaskDao.insert(params[0])
            }else{
                mAsyncTaskDao.deleteWord(params[0])
            }
            return null
        }
    }

    //    ==================== deleteWord data async =================
    private class DeleteAsyncTask internal constructor(private val mAsyncTaskDao: WordDao): AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void?): Void? {
            mAsyncTaskDao.deleteAll()
            return null
        }

    }

    private class UpdateWordAsyncTask internal constructor(private val mAsyncTaskDao: WordDao):AsyncTask<Word, Void, Void>(){
        override fun doInBackground(vararg params: Word): Void? {
            mAsyncTaskDao.updateWord(params[0])
            return null
        }

    }
}