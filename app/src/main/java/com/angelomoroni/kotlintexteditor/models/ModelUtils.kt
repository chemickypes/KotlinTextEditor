package com.angelomoroni.kotlintexteditor.models

import java.text.SimpleDateFormat

/**
 * Created by angelomoroni on 08/05/16.
 */

object FormatDate {
    fun getSubTitleRow(date: Long) : String {
        var formatter : SimpleDateFormat = SimpleDateFormat("dd-mm-yyyy")
        return formatter.format(date)
    }
}