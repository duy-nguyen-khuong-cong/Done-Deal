package com.example.myapplication.models



import android.hardware.Camera.open
import android.os.ParcelFileDescriptor.open
import android.system.Os.open
import android.util.Log
import com.example.myapplication.utils.exists
import com.example.myapplication.utils.read
import com.example.myapplication.utils.write
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import mu.KotlinLogging
import java.io.IOException
import java.nio.channels.AsynchronousFileChannel.open
import java.nio.channels.FileChannel.open
import java.nio.charset.Charset


import java.util.*
import kotlin.text.Charsets.UTF_8

private val logger = KotlinLogging.logger {}
//save file name
val JSON_FILE = "tasks.json"
//init the Gson lib
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
//Gson template init
val listType = object : TypeToken<ArrayList<Task>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class TaskJSONStore{

    companion object{
        var tasks = mutableListOf<Task>()
        fun findAll(): MutableList<Task> {
            return tasks
        }

        fun findOne(id: Long) : Task? {
            var foundTask: Task? = tasks.find { p -> p.id == id }
            return foundTask
        }

        fun create(task: Task) {//X
            task.id = generateRandomId()
            tasks.add(task)
            serialize()
        }
        fun delete(task: Task) {
            tasks.remove(task)
            serialize()
        }
        fun update(task: Task) {

            var foundTask = findOne(task.id!!)
            if (foundTask != null) {
                Log.d("FOUND", task.id.toString())
            }
            if (foundTask != null) {
                foundTask.title = task.title
                foundTask.description = task.description
                foundTask.time = task.time
                foundTask.day = task.day
                foundTask.week = task.week
                foundTask.isDone = task.isDone
                foundTask.repeat = task.repeat
                foundTask.priority = task.priority
                foundTask.subTask = task.subTask
            }
            Log.d("UPDATE","FINISH UPDATE")
            serialize()
        }

        fun reorder(task: Task,from: Int,  to: Int){

            val hour: Int = to % 24
            val fromDay: Int = Math.floor(from.toDouble() / 24).toInt()
            val toDay: Int = Math.floor(to.toDouble() / 24).toInt()
            Log.d("REORDER", hour.toString())
            Log.d("REORDER", toDay.toString())
            task.time = hour
            task.day = task.day + (toDay - fromDay)
            update(task)
            serialize()
        }

        internal fun logAll() {
            tasks.forEach { logger.info("${it}") }
        }

        private fun serialize() {
            val jsonString = gsonBuilder.toJson(tasks, listType)
            write(JSON_FILE, jsonString)
        }

        private fun deserialize() {
            val jsonString = read(JSON_FILE)
            tasks = Gson().fromJson(jsonString, listType)
        }


    }


    init {
        if (exists(JSON_FILE)) {
            //Read
            deserialize()
        }
    }


}
