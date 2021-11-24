package com.example.myapplication.adapters

import android.content.Context
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.myapplication.utils.ItemTouchHelperAdapter
import com.example.myapplication.utils.OnStartDragListener
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.animation.AnimationUtils
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.example.myapplication.R
import com.example.myapplication.models.Task
import com.example.myapplication.models.TaskJSONStore
import org.w3c.dom.Text
import java.util.*


class EventCellAdapter(
    private val context: Context,
    private val cells: MutableList<Task>,
    private val onItemListener: OnItemListener,
    private val dragListener: OnStartDragListener
): RecyclerView.Adapter<RecyclerView.ViewHolder>(), ItemTouchHelperAdapter{

    class EventViewHolder(
        val eventView: View,
        private val onItemListener: OnItemListener,
        private val cellData: MutableList<Task>
    ) : RecyclerView.ViewHolder(eventView), View.OnClickListener{

        var cellView: CardView = itemView.findViewById(com.example.myapplication.R.id.cell)
        val cellText: TextView = itemView.findViewById(com.example.myapplication.R.id.cell_text)
        val cellSub: TextView = itemView.findViewById(com.example.myapplication.R.id.sub_count)
        val cellReorder: TextView = itemView.findViewById(com.example.myapplication.R.id.cell_reorder)
        lateinit var unbinder: Unbinder
        init{
            itemView.setOnClickListener(this)

            unbinder = ButterKnife.bind(this, itemView)
        }
        override fun onClick(v: View?) {
            onItemListener.onItemClick(adapterPosition, cellData[adapterPosition])
        }


    }

    interface OnItemListener{
        fun onItemClick(position: Int, cellText: Task)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        //Inflate the cell resource file
        //inflate different cell

        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_cell,parent,false);
        return EventViewHolder(view, onItemListener, cells)

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        // Padding handling
//        val elevation: Float = holder.cellView.getMaxCardElevation()
//        val radius: Float = holder.cellView.getRadius()
//        val cos45 = Math.cos(Math.toRadians(45.0))
//
//        val horizontalPadding = (elevation + (1 - cos45) * radius).toInt()
//        val verticalPadding = (elevation * 1.5 + (1 - cos45) * radius).toInt()
//        // Pixel to dp conversion
//        var event_size= (dpToPixel(170)+ horizontalPadding) * (position)

        val item = cells[position]
        val holder: EventViewHolder = holder as EventViewHolder
            if(item.title.isNotEmpty()) {

                holder.cellView.visibility = View.VISIBLE
                holder.cellSub.visibility = View.VISIBLE
                holder.cellText.text = item.title
                holder.cellSub.text = (item.subTask.size.toString() + "Subtask")
                if(item.isDone) {
                    holder.cellText.paintFlags = holder.cellText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    holder.cellView.alpha = 0.5F
                    holder.cellSub.alpha = 0.5F
                }
                holder.cellReorder.background = when(item.priority){
                    3 -> ColorDrawable(ContextCompat.getColor(context, R.color.red))
                    2 -> ColorDrawable(ContextCompat.getColor(context, R.color.yellow))
                    else -> ColorDrawable(ContextCompat.getColor(context, R.color.coconut))
                }
                holder.cellReorder.setOnLongClickListener(object : View.OnLongClickListener {
                    override fun onLongClick(v: View?): Boolean {
                        val animation = AnimationUtils.loadAnimation(context, R.anim.fade_out)
                        holder.cellView.startAnimation(animation)
                        holder.cellSub.startAnimation(animation)
                        TaskJSONStore.delete(item)

                        return false
                    }
                })
                holder.cellReorder.setOnTouchListener(object : View.OnTouchListener {
                    override fun onTouch(view: View?, motionEvent: MotionEvent?): Boolean {
                        when (motionEvent?.action) {
                            MotionEvent.ACTION_DOWN -> dragListener.onStartDrag(holder)
                        }
                        //false: OG
                        return false

                    }
                })
            } else{
                Log.d("IN", "IM OUT ")
                holder.cellView.visibility = View.GONE
                holder.cellSub.visibility = View.GONE
            }



    }

    override fun getItemCount(): Int {
        return cells.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {

//        cells[fromPosition] = cells[toPosition].also { cells[toPosition] = cells[fromPosition] }
        TaskJSONStore.reorder(cells[fromPosition], fromPosition, toPosition)
        Collections.swap(cells, fromPosition, toPosition)

        notifyItemMoved(fromPosition,toPosition)
        return true
    }

    override fun onItemDismiss(position: Int) {
        cells.drop(position)
        notifyItemRemoved(position)
    }
}


