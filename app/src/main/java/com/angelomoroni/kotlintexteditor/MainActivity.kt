package com.angelomoroni.kotlintexteditor

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import com.angelomoroni.kotlintexteditor.adapters.NoteAdapter
import com.angelomoroni.kotlintexteditor.dao.getListNote
import com.angelomoroni.kotlintexteditor.models.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {

    var noteAdapter : NoteAdapter = NoteAdapter({n: Note -> updateNote(n)})

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

    private val READ_STORAGE_PERMISSION_CODE: Int = 120

    override fun onResume() {
        super.onResume()

        if(checkSelfPermission( Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                 //show a message to explain because it's importat get this permission
            }else{
                //show permission
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),READ_STORAGE_PERMISSION_CODE)
            }
        }else{
            //call list of note
            loadListOfNote()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            READ_STORAGE_PERMISSION_CODE  -> {
                if (grantResults.size > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    loadListOfNote()
                }else{
                    toast("We can't load notes")
                }}
        }

    }

    private fun loadListOfNote() {
        getListNote().
                subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                {n -> noteAdapter.add(n)},
                { e -> toast("Error"); e.printStackTrace() },
                {toast("List is Loaded"); noteAdapter.notifyDataSetChanged()}
        )
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
