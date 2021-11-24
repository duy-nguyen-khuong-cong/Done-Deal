package com.example.myapplication.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.WeekDays

class WeekAdapter(
    private val context: Context,
    private val weekDay: List<WeekDays>,
    private val dateList: List<Int>
): RecyclerView.Adapter<WeekAdapter.WeekViewHolder>()  {

    class WeekViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val weekDay: TextView = view.findViewById(R.id.week_day)
        val date: TextView = view.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekAdapter.WeekViewHolder {
        //Inflate week_days component
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.week_days, parent, false)
        return WeekAdapter.WeekViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: WeekAdapter.WeekViewHolder, position: Int) {

        //Take each element from the populated data array
        Log.d("SIZE: ", dateList.size.toString())
        dateList.forEach { Log.d("PRINT",it.toString()) }
        val dayOfWeek = weekDay[position]
        val day = dateList[position]
        //Assign that element to a cell in grid
        holder.weekDay.text = dayOfWeek.weekDay
        holder.date.text = day.toString()
    }

    override fun getItemCount(): Int {
        // How many to populate? All in array

        return weekDay.size
    }
}