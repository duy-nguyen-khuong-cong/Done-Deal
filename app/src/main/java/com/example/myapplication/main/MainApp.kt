package com.example.myapplication.main

import android.app.Application
import com.example.myapplication.models.Task

class MainApp : Application() {
    var gridData: MutableList<Task> = MutableList(24*7){ it -> Task() }
    override fun onCreate() {
        super.onCreate()


    }
}