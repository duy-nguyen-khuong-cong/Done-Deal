package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.EventCellAdapter
import com.example.myapplication.adapters.TimeAdapter
import com.example.myapplication.adapters.WeekAdapter
import com.example.myapplication.datas.TimeData
import com.example.myapplication.datas.WeekData
import com.example.myapplication.utils.WeekUtils
import com.example.myapplication.utils.WeekUtils.Companion.daysInWeekArray
import java.time.LocalDate
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import butterknife.ButterKnife
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.main.MainApp
import com.example.myapplication.models.WeekDays
import com.example.myapplication.utils.OnStartDragListener
import com.example.myapplication.utils.TouchHelperCallback
import java.lang.Math.floor
import com.example.myapplication.models.Task
import com.example.myapplication.models.TaskJSONStore
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity(), EventCellAdapter.OnItemListener {



    //The current week state for global use
    lateinit var dateData: MutableList<Int>
    lateinit  var weekData: MutableList<WeekDays>
    lateinit var itemTouchHelper: ItemTouchHelper
    lateinit var weekMonth: String
    lateinit var app: MainApp

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp




        setTimeView()

        WeekUtils.selectedDate = LocalDate.now()
        weekMonth = WeekUtils.weekMonthFromDate(WeekUtils.selectedDate)
        setWeekView()

        setEventCellView()
    }


    fun setTimeView(){
        // Init time frame data
        val timeData = TimeData().loadTime()

        // Apply the populate item to the Grid
        binding.timeView.adapter = TimeAdapter(this, timeData)
    }
    fun setWeekView(){
        // Init week data
        val weekMonthText: TextView = findViewById(R.id.week_month_text)
        weekMonthText.text = weekMonth
        dateData= daysInWeekArray(WeekUtils.selectedDate)
        weekData = WeekData().loadDay() as MutableList<WeekDays>
        // Grab the Grid to populate
        val weekView = findViewById<RecyclerView>(R.id.week_view)
        // Apply the populate item to the Grid
        weekView.adapter = WeekAdapter(this, weekData, dateData)
    }

    fun setEventCellView(){


        // Init time frame data
//        val tempData = MutableList<String?>((24*7)){  (it + 1).toString() }
//        val tempData = Array<String?>((168)){  "" }
        loadData()
//        tempData[1]=Task(0,"Nut", 3, "nut to NTR")

        // Apply the populate item to the Grid
        val adapter = EventCellAdapter(this, app.gridData,this, object: OnStartDragListener{
            override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                itemTouchHelper.startDrag(viewHolder)
            }
        } )
        binding.cellView.adapter = adapter

        //Drag drop operation

        ButterKnife.bind(this)
        binding.cellView.setHasFixedSize(true)
        val callback: ItemTouchHelper.Callback = TouchHelperCallback(adapter)
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.cellView)
    }
    fun prevWeek(view: android.view.View) {
        WeekUtils.selectedDate = WeekUtils.selectedDate.minusWeeks(1)
        weekMonth = WeekUtils.weekMonthFromDate(WeekUtils.selectedDate)
        setWeekView()
        setEventCellView()
    }
    fun nextWeek(view: android.view.View) {
        WeekUtils.selectedDate = WeekUtils.selectedDate.plusWeeks(1)
        weekMonth = WeekUtils.weekMonthFromDate(WeekUtils.selectedDate)
        setWeekView()
        setEventCellView()
    }
    // Load raw Json data to a list
    fun loadData(){
        TaskJSONStore()
        app.gridData = MutableList(24*7){ it -> Task() }
        if(TaskJSONStore.tasks.isNotEmpty()){
            for (task in TaskJSONStore.tasks){
                val idx = dateData.indexOf(task.day)
                if( idx!= -1 && task.repeat == true){
                    for( i in 0..6) app.gridData[i*24 + task.time] = task
                }
                else if ( idx!= -1 && task.week == weekMonth.split(" ")[2].toInt()){

                    app.gridData[idx*24 + task.time] = task
                }
            }

        }
    }
    private fun getJSONFromAssets(): String?{
        var json: String? = null
        val charset: Charset = Charsets.UTF_8
        try{
            val `is` = assets.open("tasks.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, charset)

        } catch (ex: IOException){
            ex.printStackTrace()
            return null
        }
        return json
    }
    override fun onItemClick(position: Int, cellText: Task) {
        val hour = position % 24
        val weekDay: Int = floor(position.toDouble() / 24).toInt()
        val day: Int = dateData[weekDay]
        val intent = Intent(this, TaskEditActivity::class.java)
        intent.putExtra("position", position)
        intent.putExtra("hour", hour)
        intent.putExtra("day", day)
        intent.putExtra("week", weekMonth.split(" ")[2].toInt())

        if(cellText?.title != "" ){
            intent.putExtra("task", cellText)
        } else intent.putExtra("task", Task())
        this.startActivity(intent)
//        Toast.makeText(this, weekData[weekDay].weekDay + ", " + day.toString() + " at " + hour.toString() + ":00"  , Toast.LENGTH_SHORT).show()
        setEventCellView()
    }



}