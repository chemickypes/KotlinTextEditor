package com.angelomoroni.kotlintexteditor

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.angelomoroni.kotlintexteditor.adapters.NoteAdapter
import com.angelomoroni.kotlintexteditor.models.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var noteAdapter : NoteAdapter = NoteAdapter(getFakeNoteList(),
            {n: Note -> toast(n.title)
                updateNote(n)})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton?
        fab?.setOnClickListener({ view ->
            createNote() })

        // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()

        val list = findViewById(R.id.list) as RecyclerView?
        list?.layoutManager = LinearLayoutManager(this)

        list?.adapter = noteAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK
        && requestCode == NOTE_DETAIL_ACTIVITY_REQUEST){
            var n : Note = data?.getParcelableExtra<Note>(NOTE_KEY) as Note
            if (n.id == -1){
                noteAdapter.add(n)
            }else{
                noteAdapter.replace(n)
            }
            noteAdapter.notifyDataSetChanged()
        }
    }



    fun getFakeNoteList() : ArrayList<Note>{
        var list = ArrayList<Note>();
        for (i in 1..5){
            var n: Note = Note("Note Title ${i}","Body text ${i}")
            n.id = i
            list.add(n)
        }

        return list
    }

    fun AppCompatActivity.toast(messge: String, l: Int = Toast.LENGTH_SHORT){
        Toast.makeText(this,messge,l).show()
    }

    fun createNote(){
        //openInputDialog()
        var n : Note = Note(FormatDate.getHint())
        var i : Intent = Intent(this,NoteActivity::class.java)
        i.putExtra(NOTE_KEY,n)
        startActivityForResult(i,NOTE_DETAIL_ACTIVITY_REQUEST)
    }

    fun createNote(title1: Editable?,title2:String){
        var ti :String = if(title1!!.length > 0) title1.toString() else title2
        var n : Note = Note(ti)
        var i : Intent = Intent(this,NoteActivity::class.java)
        i.putExtra(NOTE_KEY,n)
        startActivityForResult(i,NOTE_DETAIL_ACTIVITY_REQUEST)
    }

    private fun openInputDialog() {
        var v = LayoutInflater.from(this).inflate(R.layout.input_dialog_layout,null);
        var alertDialogBuilder : AlertDialog.Builder = AlertDialog.Builder(this,R.style.DialogTheme)
        alertDialogBuilder.setView(v)

        val input : EditText = v.findViewById(R.id.new_title) as EditText
        input.hint = FormatDate.getHint()

        alertDialogBuilder.setPositiveButton(R.string.create_note,
                { d : DialogInterface,id: Int -> createNote(input.text,FormatDate.getHint()) })
        alertDialogBuilder.setNegativeButton(R.string.cancel,{
            d:DialogInterface,id:Int -> //noting
        })

        alertDialogBuilder.create().show()
    }

    fun updateNote(n : Note){
        var i : Intent = Intent(this,NoteActivity::class.java)
        i.putExtra(NOTE_KEY,n);
        startActivityForResult(i,NOTE_DETAIL_ACTIVITY_REQUEST)
    }
}
