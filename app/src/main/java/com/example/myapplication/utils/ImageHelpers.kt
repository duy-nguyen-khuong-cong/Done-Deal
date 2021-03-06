package com.example.myapplication.utils

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.myapplication.R

fun showImagePicker(intentLauncher : ActivityResultLauncher<Intent>) {
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/*"
    chooseFile = Intent.createChooser(chooseFile, R.string.add_img.toString())
    intentLauncher.launch(chooseFile)
}