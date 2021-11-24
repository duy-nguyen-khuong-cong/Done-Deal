package com.example.myapplication.models

import java.io.Serializable
import java.time.LocalDate

data class Task(
    var id: Long =0,
    var title: String = "",
    var priority: Int =1,
    var description: String = "",
    var isDone: Boolean = false,
//    var start: LocalDate = LocalDate.now(),
//    var end: LocalDate = LocalDate.now(),
    var day: Int = 1,
    var time: Int = 1,
    var week: Int = 1,
    var subTask: MutableList<Task?> = mutableListOf(),
    var repeat: Boolean = false
): Serializable{

}