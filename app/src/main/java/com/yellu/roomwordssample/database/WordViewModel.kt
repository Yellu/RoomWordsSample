package com.yellu.roomwordssample.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
/**
Created by yellappa on 07,July,2019
 */
class WordViewModel(application: Application) : AndroidViewModel(application) {
    private var mRepository:WordRepository = WordRepository(application)
    private var mAllWords:LiveData<List<Word>>

    init {
        mAllWords = mRepository.getAllWords()
    }

    fun getAllWords():LiveData<List<Word>>{
        return mAllWords;
    }

    fun insert(word: Word){
        mRepository.insert(word)
    }

    fun deleteAll(){
        mRepository.deleteAll()
    }

    fun deleteWord(word: Word){
        mRepository.deleteWord(word)
    }

    fun updateWord(word: Word){
        mRepository.updateWord(word)
    }

}