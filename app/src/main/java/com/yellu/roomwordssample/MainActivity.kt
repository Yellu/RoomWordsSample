package com.yellu.roomwordssample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import com.yellu.roomwordssample.database.WordViewModel
import androidx.lifecycle.ViewModelProviders
import com.yellu.roomwordssample.database.Word
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper

class MainActivity : AppCompatActivity() {

    private var adapter:WordListAdapter?= null

    private var mWordViewModel:WordViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        adapter = WordListAdapter(this) { partItem : Word -> partItemClicked(partItem) }

        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.setHasFixedSize(true)
        recyclerview.adapter = adapter


        fab.setOnClickListener {
            startActivityForResult(Intent(this, NewWordActivity::class.java), 1111)
        }

        mWordViewModel!!.getAllWords().observe(this, Observer {
          adapter!!.setWords(it)
        })


        // Add the functionality to swipe items in the
// recycler view to delete that item
        val helper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val myWord = adapter!!.getWordAtPosition(position)
                    Toast.makeText(this@MainActivity, "Deleting " + myWord.mWord, Toast.LENGTH_LONG).show()

                    // Delete the word
                    mWordViewModel!!.deleteWord(myWord)
                }
            })
        helper.attachToRecyclerView(recyclerview)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_delete -> {
                mWordViewModel!!.deleteAll()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun partItemClicked(word : Word) {
        Toast.makeText(this, "Clicked: ${word.mWord}", Toast.LENGTH_LONG).show()
        val intent = Intent(this, NewWordActivity::class.java)
        intent.putExtra("id", word.id)
        intent.putExtra("word", word.mWord)

        startActivityForResult(intent, 1111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1111 && resultCode == Activity.RESULT_OK){
            val id:Int = data!!.getIntExtra("id", 0)
            val wordStr:String = data.getStringExtra("word")
            val word = Word(id, wordStr)

            if(id == 0){
                mWordViewModel!!.insert(word)
            } else{
                mWordViewModel!!.updateWord(word)
            }
        }
    }
}
