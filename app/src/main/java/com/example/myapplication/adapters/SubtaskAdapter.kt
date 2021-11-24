package com.example.myapplication.adapters

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Task

class SubtaskAdapter(
    private val context: Context,
    private val dataset: MutableList<Task?>
): RecyclerView.Adapter<SubtaskAdapter.SubtaskViewHolder>()  {

    class SubtaskViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.subtask_title)
        val checkView: CheckBox = view.findViewById(R.id.cbDone)
        val removeView: ImageView = view.findViewById(R.id.remove_subtask)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubtaskViewHolder {
        //Inflate subtask_frame component
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.subtask_item, parent, false)
        return SubtaskViewHolder(adapterLayout)
    }

    private fun toggleStrikeThrough(textView: TextView, isChecked: Boolean) {
        if(isChecked) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
    override fun onBindViewHolder(holder: SubtaskViewHolder, position: Int) {

        //Take each element from the populated data array
        val item = dataset[position]
        //Assign that element to a cell in grid
        if (item != null) {
            holder.textView.text = item.title
        }
        if (item != null) {
            holder.checkView.isChecked = item.isDone
        }
        // change textview to either slashed or not depends on isDone
        toggleStrikeThrough(holder.textView, holder.checkView.isChecked)

        //Setup onChanged listener
        holder.checkView.setOnCheckedChangeListener{ _, isChecked ->
            toggleStrikeThrough(holder.textView, isChecked)
            if (item != null) {
                item.isDone = !item.isDone
            }

        }

        holder.removeView.setOnClickListener{
            Log.d("DELETE SUB", position.toString())
            dataset.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        // How many to populate? All in array

        return dataset.size
    }
}