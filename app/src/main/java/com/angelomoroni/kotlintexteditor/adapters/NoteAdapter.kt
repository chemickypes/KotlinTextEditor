package com.angelomoroni.kotlintexteditor.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.angelomoroni.kotlintexteditor.R
import com.angelomoroni.kotlintexteditor.models.Note
import kotlinx.android.synthetic.main.note_item_row.view.*

/**
 * Created by angelomoroni on 08/05/16.
 */
class NoteAdapter (val items : List<Note>, val itemClick : (Note) -> Unit)
: RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        var view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.note_item_row,parent,false);
        return ViewHolder(view,itemClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View?, val itemClick: (Note) -> Unit) : RecyclerView.ViewHolder(itemView){


        fun bind(note : Note){
            with(note){
                itemView.text.text = note.title
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}