package com.example.myapplication.datas

import com.example.myapplication.R
import com.example.myapplication.models.Time

class TimeData {
    fun loadTime(): List<Time>{
        return listOf<Time>(
            Time(R.string.time_00,24),
            Time(R.string.time_01,1),
            Time(R.string.time_02,2),
            Time(R.string.time_03,3),
            Time(R.string.time_04,4),
            Time(R.string.time_05,5),
            Time(R.string.time_06,6),
            Time(R.string.time_07,7),
            Time(R.string.time_08,8),
            Time(R.string.time_09,9),
            Time(R.string.time_10,10),
            Time(R.string.time_11,11),
            Time(R.string.time_12,12),
            Time(R.string.time_13,13),
            Time(R.string.time_14,14),
            Time(R.string.time_15,15),
            Time(R.string.time_16,16),
            Time(R.string.time_17,17),
            Time(R.string.time_18,18),
            Time(R.string.time_19,19),
            Time(R.string.time_20,20),
            Time(R.string.time_21,21),
            Time(R.string.time_22,22),
            Time(R.string.time_23,23),

        )
    }
}