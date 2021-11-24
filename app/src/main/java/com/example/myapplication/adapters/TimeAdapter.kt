package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Time

class TimeAdapter(
    private val context: Context,
    private val dataset: List<Time>
    ): RecyclerView.Adapter<TimeAdapter.TimeViewHolder>()  {

    class TimeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.time_value)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeViewHolder {
        //Inflate time_frame component
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.time_frame, parent, false)
        return TimeViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: TimeViewHolder, position: Int) {

        //Take each element from the populated data array
        val item = dataset[position]
        //Assign that element to a cell in grid
        holder.textView.text = context.resources.getString(item.timeStringResourceId)

    }

    override fun getItemCount(): Int {
        // How many to populate? All in array

        return dataset.size
    }
}