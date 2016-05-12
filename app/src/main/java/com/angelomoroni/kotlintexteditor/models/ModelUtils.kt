package com.angelomoroni.kotlintexteditor.models

import java.text.SimpleDateFormat
import java.util.*

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