package com.yellu.roomwordssample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.yellu.roomwordssample.database.Word
import kotlinx.android.synthetic.main.new_word_activity.*
/**
Created by yellappa on 07,July,2019
 */
class NewWordActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_word_activity)

        val word = intent.getStringExtra("word")
        val id = intent.getIntExtra("id", 0)

        if(!TextUtils.isEmpty(word)) {
            edit_word.setText(word)
        }

        button_save.setOnClickListener {
            val intent = Intent()

            val wordStr = edit_word.text.toString()
            if(TextUtils.isEmpty(wordStr)){
                setResult(Activity.RESULT_CANCELED, intent)
            } else {
                intent.putExtra("id", id)
                intent.putExtra("word", wordStr)
                setResult(Activity.RESULT_OK, intent)
            }
            finish()
        }
    }
}