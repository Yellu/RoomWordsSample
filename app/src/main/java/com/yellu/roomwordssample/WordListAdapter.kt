package com.yellu.roomwordssample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.yellu.roomwordssample.database.Word
import com.yellu.roomwordssample.databinding.RecyclerviewItemBinding
/**
Created by yellappa on 07,July,2019
 */

class WordListAdapter(val onClickListener: (Word) -> Unit) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {
    private var mWords:List<Word>? = null

    fun setWords(mWords:List<Word>){
        this.mWords = mWords
        notifyDataSetChanged()
    }

    fun getWordAtPosition(position: Int): Word {
        return mWords!![position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding:RecyclerviewItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.recyclerview_item, parent, false)
        return WordViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (mWords != null){
            mWords!!.size
        } else{
            0
        }
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word:Word = mWords!![position]
        holder.viewBinding.setVariable(BR.word, word)
        holder.viewBinding.executePendingBindings()

        holder.itemView.setOnClickListener{
            onClickListener(this.mWords!![position])
        }
    }

    class WordViewHolder(binding: RecyclerviewItemBinding):RecyclerView.ViewHolder(binding.root){
        val viewBinding : RecyclerviewItemBinding = binding

    }
}