package com.example.myapplication.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.adapters.SubtaskAdapter
import com.example.myapplication.databinding.ActivityTaskEditBinding
import com.example.myapplication.models.Task
import com.example.myapplication.models.TaskJSONStore

class TaskEditActivity: AppCompatActivity(){
    private lateinit var binding: ActivityTaskEditBinding
    lateinit var task: Task
    var subtaskList: MutableList<Task?> = mutableListOf()
    var idCount: Long = 0
    //Pass time from mainActivity layout
    private lateinit var taskTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskEditBinding.inflate(layoutInflater)
        setContentView(binding.root)




        task = intent?.extras?.getSerializable("task") as Task
        subtaskList = task.subTask
        binding.checkBox.isChecked = task.isDone
        binding.titleText.setText(task.title)
        binding.descText.setText(task.description)
        binding.priority.check(when(task.priority){
            3 -> R.id.important
            2 -> R.id.normal
            else -> R.id.chill
        })
        binding.dailySwitch.isChecked = task.repeat
        setSubtask()

    }

    fun setSubtask() {
        // Init test data

        // Apply the populate item to the Grid
        binding.subtask.adapter = SubtaskAdapter(this, subtaskList)
    }
    fun saveTask(view: android.view.View) {
        val pos = intent?.extras?.getInt("position")
        val hour = intent?.extras?.getInt("hour")
        val day = intent?.extras?.getInt("day")
        val week = intent?.extras?.getInt("week")
        val priority = when(binding.priority.checkedRadioButtonId){
            R.id.important -> 3
            R.id.normal -> 2
            else -> 1
        }
        if(task.title ==""){
            var newTask = Task(1,binding.titleText.text.toString(),priority, binding.descText.text.toString(),binding.checkBox.isChecked,day!!, hour!!, week!!,subtaskList,binding.dailySwitch.isChecked)
            TaskJSONStore.create(newTask)
        } else{
            Log.d("UPDATE", task.title)
            TaskJSONStore.update(Task(task.id,binding.titleText.text.toString(),priority, binding.descText.text.toString(),binding.checkBox.isChecked,day!!, hour!!, week!!,subtaskList,binding.dailySwitch.isChecked))
        }

        // done with this intent activity
        finish()
    }


    fun addSubtask(view: android.view.View) {
        subtaskList.add(Task(idCount, binding.addSubtaskText.text.toString()))
        idCount++
        setSubtask()
    }
}