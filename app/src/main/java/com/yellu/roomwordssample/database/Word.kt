package com.yellu.roomwordssample.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/**
Created by yellappa on 07,July,2019
 */

@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "word")
    val mWord:String
)