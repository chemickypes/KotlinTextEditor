package com.angelomoroni.kotlintexteditor.models

import java.util.*
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by angelomoroni on 08/05/16.
 */
data class Note (var title: String,
                 var body: String,
                 var creatTime: Long = System.currentTimeMillis(),
                 var lastModTime: Long = System.currentTimeMillis()) : Parcelable {
    constructor(source: Parcel): this(source.readString(), source.readString(), source.readLong(), source.readLong())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(title)
        dest?.writeString(body)
        dest?.writeLong(creatTime)
        dest?.writeLong(lastModTime)
    }

    companion object {
        @JvmField final val CREATOR: Parcelable.Creator<Note> = object : Parcelable.Creator<Note> {
            override fun createFromParcel(source: Parcel): Note {
                return Note(source)
            }

            override fun newArray(size: Int): Array<Note?> {
                return arrayOfNulls(size)
            }
        }
    }
}