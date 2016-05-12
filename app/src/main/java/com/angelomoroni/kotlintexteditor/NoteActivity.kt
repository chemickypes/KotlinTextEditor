package com.angelomoroni.kotlintexteditor


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

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.angelomoroni.kotlintexteditor.dao.removeNoteDAO
import com.angelomoroni.kotlintexteditor.models.DELETE_NOTE_CODE
import com.angelomoroni.kotlintexteditor.models.NOTE_KEY
import com.angelomoroni.kotlintexteditor.models.Note
import kotlinx.android.synthetic.main.activity_note.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

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

        if(note?.id == -1L){
            finish()
        }else {

            var s: Snackbar = Snackbar.make(contentPanel, R.string.want_remove_note, Snackbar.LENGTH_LONG)
            s.setAction(R.string.remove_note, {
                removeNoteDAO(note as Note)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe (
                                { b: Boolean ->
                                    if (b) {
                                        var intent: Intent = Intent()
                                        intent.putExtra(NOTE_KEY, note)

                                        setResult(DELETE_NOTE_CODE, intent);
                                        finish()
                                    } else {
                                        snack(getString(R.string.note_not_deleted))
                                    }
                                },
                                { e -> e.printStackTrace(); snack(getString(R.string.note_not_deleted)) }
                        )
            })

            s.show()
        }


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

    fun AppCompatActivity.snack(message: String, action: (v: View)  -> Unit = {}, actionName: String = ""){
        Snackbar.make(contentPanel,message,Snackbar.LENGTH_LONG).
                setAction(actionName,action).show()
    }
}
