package com.yellu.roomwordssample

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yellu.roomwordssample.database.Word
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class WordListAdapter(context: Context, val onClickListener:(Word) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var inflater:LayoutInflater = LayoutInflater.from(context)
    private var mWords:List<Word>? = null

    fun setWords(mWords:List<Word>){
        this.mWords = mWords
        notifyDataSetChanged()
    }

    fun getWordAtPosition(position: Int): Word {
        return mWords!![position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view:View = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (mWords != null){
            mWords!!.size
        } else{
            0
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (mWords != null){
            val word:Word = mWords!![position]
            holder.itemView.textView.text = word.mWord
            Log.d("Word", "Word id===> " + word.id)
        } else{
            holder.itemView.textView.text = "No Word"
        }
        holder.itemView.setOnClickListener{
            onClickListener(this.mWords!![position])
        }
    }

    class WordViewHolder(view:View):RecyclerView.ViewHolder(view)
}