package com.angelomoroni.kotlintexteditor.models

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by angelomoroni on 08/05/16.
 */

object FormatDate {

    fun getHint():String {
        var cTime :Calendar = Calendar.getInstance()

        var formatter : SimpleDateFormat = SimpleDateFormat("hh_mm_dd_MM")
        return "note_" + formatter.format(cTime.time)
    }

    fun getSubTitleRow(date: Long) : String {

        var cTime :Calendar = Calendar.getInstance()
        cTime.timeInMillis = date //date last mod

        var cY :Calendar = Calendar.getInstance()
        cY.add(Calendar.DAY_OF_YEAR, -1)

        if(sameDay(cTime)){
            return "Today"
        }else if(sameDay(cTime,cY)){
            return "Yesterday"
        }else{
            var formatter : SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
            return formatter.format(date)
        }


    }

    fun sameDay(c1 :Calendar, c2: Calendar = Calendar.getInstance()) :  Boolean{
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR))
    }
}