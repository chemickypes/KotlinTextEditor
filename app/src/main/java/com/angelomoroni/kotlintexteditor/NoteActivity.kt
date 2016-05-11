package com.angelomoroni.kotlintexteditor

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.angelomoroni.kotlintexteditor.models.DELETE_NOTE_CODE
import com.angelomoroni.kotlintexteditor.models.NOTE_KEY
import com.angelomoroni.kotlintexteditor.models.Note
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {

    var note : Note? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        note = intent.getParcelableExtra(NOTE_KEY)

        titlenote.text.insert(0,note?.title)
        bodynote.text.insert(0,note?.body)
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
        if (id == R.id.action_save) {
            saveNote();
            return true
        }else if(id == android.R.id.home){
            setResult(Activity.RESULT_CANCELED)
            finish()
            return true
        }else if(id == R.id.action_delete){
            deleteNote();
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {

        var intent : Intent = Intent()
        intent.putExtra(NOTE_KEY,note)

        setResult(DELETE_NOTE_CODE,intent);
        finish()
    }

    private fun saveNote() {
        note!!.title = titlenote.text.toString()
        note!!.body = bodynote.text.toString()
        note!!.lastModTime = System.currentTimeMillis()

        var intent : Intent = Intent()
        intent.putExtra(NOTE_KEY,note)

        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        super.onBackPressed()
    }
}
