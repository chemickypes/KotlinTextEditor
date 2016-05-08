package com.angelomoroni.kotlintexteditor.models

/**
 * Created by angelomoroni on 08/05/16.
 */
data class Note (var title: String,
                 var body: String,
                 var creatTime: Long = System.currentTimeMillis(),
                 var lastModTime: Long = System.currentTimeMillis()){
}