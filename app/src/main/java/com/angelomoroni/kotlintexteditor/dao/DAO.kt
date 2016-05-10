package com.angelomoroni.kotlintexteditor.dao

import android.content.Context
import android.os.Environment
import com.angelomoroni.kotlintexteditor.models.Note
import com.google.gson.Gson
import rx.Observable
import java.io.File

/**
 * Created by angelomoroni on 10/05/16.
 */

val NOTE_STORAGE :String = "KTextEditorDir"

fun getListNote() : Observable<Note>{
    return Observable.defer { Observable.from(getListoFromExternalStorage())}
}

fun getListoFromExternalStorage(): Array<Note>? {

    var listNote :Array<Note>? = null

    var folder : File = File(Environment.getExternalStorageDirectory().getAbsoluteFile(), NOTE_STORAGE)
    if(folder.mkdirs()||folder.isDirectory()){
        var listFile = folder.listFiles()

        listNote = Array<Note>(listFile?.size ?: 0,
                { i -> getNote(listFile[i].readText()) })
    }
    return listNote;
}

fun getNote(readText: String): Note {
    return Gson().fromJson(readText,Note::class.java)
}


fun saveNote(n:Note): Observable<Note> {
    return Observable.defer{ Observable.just(saveNoteOnExternalStorage(n))}
}

fun saveNoteOnExternalStorage(n: Note): Note {
    var folder : File = File(Environment.getExternalStorageDirectory().getAbsoluteFile(), NOTE_STORAGE)
    var file : File = File(folder.absolutePath,n.title)
    file.writeText(Gson().toJson(n))

    return n
}
