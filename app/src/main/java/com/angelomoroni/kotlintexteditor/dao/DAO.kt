package com.angelomoroni.kotlintexteditor.dao

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

import android.content.Context
import android.os.Environment
import android.util.Log
import com.angelomoroni.kotlintexteditor.models.Note
import com.google.gson.Gson
import rx.Observable
import java.io.DataOutput
import java.io.File
import java.util.*

/**
 * Created by angelomoroni on 10/05/16.
 */

val NOTE_STORAGE :String = "KTextEditorDir"

fun getListNote() : Observable<Note?>{
    return Observable.defer { Observable.from(getListoFromExternalStorage())}
}

fun getListoFromExternalStorage(): Array<Note?>? {

    var listNote :Array<Note?>? = null

    var folder : File = File(Environment.getExternalStorageDirectory().getAbsoluteFile(), NOTE_STORAGE)
    if(folder.mkdirs()||folder.isDirectory()){
        var listFile = folder.listFiles()

        listNote = Array<Note?>(listFile?.size ?: 0,
                { i -> if(listFile[i].isFile)getNote(listFile[i].readText()) else null})
    }
    return listNote;
}

val TAG: String? = "DAO"

fun getNote(readText: String): Note {
    Log.d(TAG,readText)
    return Gson().fromJson(readText,Note::class.java)
}


fun saveNote(n:Note): Observable<Note> {
    return Observable.defer{ Observable.just(saveNoteOnExternalStorage(n))}
}

fun removeNoteDAO(n: Note) : Observable<Boolean>{
    return Observable.defer {
        Observable.just(removeNoteFromExternaStorage(n))
    }
}

fun removeNoteFromExternaStorage(n: Note): Boolean {
    var folder : File = File(Environment.getExternalStorageDirectory().getAbsoluteFile(), NOTE_STORAGE)
    folder.mkdirs()
    var an = folder.absolutePath + File.separator + n.id;
    var file : File = File(an)
    return file.delete()
}

fun saveNotes(l: ArrayList<Note?>) : Observable<Boolean>{
    return Observable.defer {
        for ( n in l){
            saveNoteOnExternalStorage(n as Note);
        };

        Observable.just(true)
    }
}

fun saveNoteOnExternalStorage(n: Note): Note {
    var folder : File = File(Environment.getExternalStorageDirectory().getAbsoluteFile(), NOTE_STORAGE)
    folder.mkdirs()
    var an = folder.absolutePath + File.separator + n.id;
    var file : File = File(an)
    if(!file.exists()){
        file.createNewFile()
    }
    file.writeText(Gson().toJson(n))

    return n
}
