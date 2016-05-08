package com.angelomoroni.kotlintexteditor

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.angelomoroni.kotlintexteditor.adapters.NoteAdapter
import com.angelomoroni.kotlintexteditor.models.NOTE_DETAIL_ACTIVITY
import com.angelomoroni.kotlintexteditor.models.NOTE_DETAIL_ACTIVITY_REQUEST
import com.angelomoroni.kotlintexteditor.models.NOTE_KEY
import com.angelomoroni.kotlintexteditor.models.Note
import java.util.*

class MainActivity : AppCompatActivity() {

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
        list?.adapter = NoteAdapter(getFakeNoteList(),
                {n: Note -> toast(n.title)
                updateNote(n)})
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
        val id = item.itemId

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun getFakeNoteList() : List<Note>{
        var list = ArrayList<Note>();
        for (i in 1..5){
            list.add(Note("Note Title ${i}","Body text ${i}"))
        }

        return list
    }

    fun AppCompatActivity.toast(messge: String, l: Int = Toast.LENGTH_SHORT){
        Toast.makeText(this,messge,l).show()
    }

    fun createNote(){

    }

    fun updateNote(n : Note){
        var i : Intent = Intent(this,NoteActivity::class.java)
        i.putExtra(NOTE_KEY,n);
        startActivityForResult(i,NOTE_DETAIL_ACTIVITY_REQUEST)
    }
}
