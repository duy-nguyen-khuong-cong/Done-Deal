package com.example.myapplication.activities

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.adapters.SubtaskAdapter
import com.example.myapplication.databinding.ActivityTaskEditBinding
import com.example.myapplication.models.Task
import com.example.myapplication.models.TaskJSONStore
import com.example.myapplication.utils.showImagePicker
import com.squareup.picasso.Picasso
import java.util.*

class TaskEditActivity: AppCompatActivity(){
    private lateinit var binding: ActivityTaskEditBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    lateinit var task: Task
    var subtaskList: MutableList<Task?> = mutableListOf()
    var idCount: Long = 0
    //Pass time from mainActivity layout
    private lateinit var taskTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskEditBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.taskImg.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }
        registerImagePickerCallback()

        task = intent?.extras?.getSerializable("task") as Task
        subtaskList = task.subTask
        binding.checkBox.isChecked = task.isDone
        binding.titleText.setText(task.title)
        binding.descText.setText(task.description)
        if(task.img != "") Picasso.get().load(task.img).into(binding.taskImg)
        binding.priority.check(when(task.priority){
            3 -> R.id.important
            2 -> R.id.normal
            else -> R.id.chill
        })
        binding.dailySwitch.isChecked = task.repeat
        setSubtask()

    }
    fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            task.img = result.data!!.data.toString()!!
                            Picasso.get()
                                .load(task.img)
                                .into(binding.taskImg)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cancel, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    fun setSubtask() {
        // Apply the populate item to the Grid
        binding.subtask.adapter = SubtaskAdapter(this, subtaskList)
    }
    fun saveTask(view: android.view.View) {
        val pos = intent?.extras?.getInt("position")
        val hour = intent?.extras?.getInt("hour")
        val day = intent?.extras?.getInt("day")
        val week = intent?.extras?.getInt("week")
        val month = intent?.extras?.getInt("month")
        val year = intent?.extras?.getInt("year")
        val priority = when(binding.priority.checkedRadioButtonId){
            R.id.important -> 3
            R.id.normal -> 2
            else -> 1
        }
        if(task.title ==""){
            var newTask = Task(1,binding.titleText.text.toString(),priority, binding.descText.text.toString(),task.img,binding.checkBox.isChecked,day!!, hour!!, week!!,subtaskList,binding.dailySwitch.isChecked)
            TaskJSONStore.create(newTask)

            // Send to google calendar
            val beginTime: Calendar = Calendar.getInstance()
            val endTime: Calendar = Calendar.getInstance()

            beginTime.clear()
            endTime.clear()
            if (year != null && month != null) {
                beginTime.set(year, month-1, day, hour,0)
                endTime.set(year, month-1, day, hour + 1,0)
            }
            var startMillis = beginTime.timeInMillis
            var endMillis = endTime.timeInMillis



            if(binding.googleSwitch.isChecked) {
                val intent: Intent = Intent(Intent.ACTION_INSERT)
                intent.setData(CalendarContract.Events.CONTENT_URI)
                intent.putExtra(CalendarContract.Events.TITLE, binding.titleText.text.toString())
                intent.putExtra(
                    CalendarContract.Events.DESCRIPTION,
                    binding.descText.text.toString()
                )
                intent.putExtra(
                    CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                    startMillis
                )//WHY U STILL DECEMBER
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
                if (binding.dailySwitch.isChecked) {
                    Log.d("RRULE", "IN")
                    intent.putExtra(CalendarContract.Events.RRULE, "FREQ=DAILY")
                }
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this,
                        "THERE IS NO APP THAT SUPPORT THIS ACTION",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


        } else{
            Log.d("UPDATE", task.title)
            TaskJSONStore.update(Task(task.id,binding.titleText.text.toString(),priority, binding.descText.text.toString(),task.img,binding.checkBox.isChecked,day!!, hour!!, week!!,subtaskList,binding.dailySwitch.isChecked))
        }


        // done with this intent activity
        finish()
    }


    fun addSubtask(view: android.view.View) {
        subtaskList.add(Task(idCount, binding.addSubtaskText.text.toString()))
        binding.addSubtaskText.text.clear()
        idCount++
        setSubtask()
    }
}