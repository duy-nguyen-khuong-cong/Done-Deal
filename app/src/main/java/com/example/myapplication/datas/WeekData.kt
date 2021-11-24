package com.example.myapplication.datas

import com.example.myapplication.R
import com.example.myapplication.models.WeekDays

class WeekData {
    fun loadDay(): List<WeekDays>{
        return listOf<WeekDays>(

            WeekDays("Mon"),
            WeekDays("Tue"),
            WeekDays("Wed"),
            WeekDays("Thu"),
            WeekDays("Fri"),
            WeekDays("Sat"),
            WeekDays("Sun"),


        )
    }
}