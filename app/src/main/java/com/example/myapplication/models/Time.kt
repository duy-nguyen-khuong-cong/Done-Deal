package com.example.myapplication.models;


import androidx.annotation.StringRes;

data class Time (
    @StringRes
    val timeStringResourceId: Int,

    val timeValueResourceId: Int
    ) {

}
