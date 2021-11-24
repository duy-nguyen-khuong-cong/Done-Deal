package com.example.myapplication.utils

import android.graphics.Canvas
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import kotlin.math.abs

class TouchHelperCallback: ItemTouchHelper.Callback {
    companion object{
        val ALPHA_FULL: Float = 1.0F
        lateinit var adapter: ItemTouchHelperAdapter
    }
    constructor(_adapter: ItemTouchHelperAdapter){
        adapter = _adapter
    }

    override fun isLongPressDragEnabled(): Boolean {
        return false
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        if(recyclerView.layoutManager is GridLayoutManager ){
            val dragFlags: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            val swipeFlag =0;
            return makeMovementFlags(dragFlags,swipeFlag)
        } else{
            val dragFlags: Int = ItemTouchHelper.UP or ItemTouchHelper.DOWN
            val swipeFlag =ItemTouchHelper.START or ItemTouchHelper.END
            return makeMovementFlags(dragFlags,swipeFlag)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        if(viewHolder.itemViewType != target.itemViewType){
            return false
        }

        adapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        adapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        viewHolder.itemView.alpha = ALPHA_FULL
        if(viewHolder is ItemTouchHelper){
            val itemTouchHelperViewHolder: ItemTouchHelperViewHolder = viewHolder as ItemTouchHelperViewHolder
            itemTouchHelperViewHolder.onItemClear()
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            if(viewHolder is ItemTouchHelperViewHolder){
                val itemTouchHelperViewHolder: ItemTouchHelperViewHolder = viewHolder as ItemTouchHelperViewHolder
                itemTouchHelperViewHolder.onItemSelected()
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val alpha: Float = ALPHA_FULL - abs(dX) / viewHolder.itemView.width.toFloat()
            viewHolder.itemView.alpha = alpha
            viewHolder.itemView.translationX = dX
        } else
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
}