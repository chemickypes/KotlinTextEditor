package com.angelomoroni.kotlintexteditor.adapters

/*
    Copyright 2016 Angelo Moroni

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angelomoroni.kotlintexteditor.R
import com.angelomoroni.kotlintexteditor.models.FormatDate
import com.angelomoroni.kotlintexteditor.models.Note
import kotlinx.android.synthetic.main.note_item_row.view.*
import java.util.*

/**
 * Created by angelomoroni on 08/05/16.
 */
class NoteAdapter (val itemClick : (Note) -> Unit,
                   val longItemClick : ((Note) -> Boolean)? = null , val items : ArrayList<Note> = ArrayList())
: RecyclerView.Adapter<NoteAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        var view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.note_item_row,parent,false);
        return ViewHolder(view,itemClick,longItemClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View?, val itemClick: (Note) -> Unit,
                     val longItemClick : ((Note) -> Boolean)?) : RecyclerView.ViewHolder(itemView){


        fun bind(note : Note){
            with(note){
                itemView.text.text = note.title
                itemView.date.text = FormatDate.getSubTitleRow(note.lastModTime)
                itemView.setOnClickListener { itemClick(this) }
                if(longItemClick != null)itemView.setOnLongClickListener {
                    (longItemClick as (Note) -> Boolean) (this)  }
            }
        }
    }

    fun add(n: Note?) {
        //n?.id = itemCount
        items.add(n as Note)
    }

    fun replace (n: Note){
        for(note in items){
            if( note.id == n.id){
                note.title = n.title
                note.body = n.body
                note.lastModTime = n.lastModTime
            }
        }
    }

    fun remove(n: Note) {
        items.remove(n)
    }
}