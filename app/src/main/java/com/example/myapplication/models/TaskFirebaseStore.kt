//package com.example.myapplication.models
//
//import android.util.Log
//import android.view.View
//import android.view.animation.AnimationUtils
//import com.example.myapplication.R
//import com.google.firebase.database.*
//// TO BE CONTINUE
//class TaskFirebaseStore {
//
//    companion object{
//        var mReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("tasks")
//        var tasks = mutableListOf<Task>()
//
//        fun getTasks(){
//            mReference.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(p0: DataSnapshot) {
//                    tasks.clear()
//                    for(keyNode in p0.children){
//                        val task: Task? = keyNode.getValue(Task::class.java)
//                        if (task != null) {
//                            tasks.add(task)
//                        }
//                    }
//                }
//
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//            })
//        }
//
//        fun createTask(task: Task){
//            val key = mReference.push().key
//
//            if (key != null) {
//                Log.d("FIRE", key)
//                task.id = key
//                mReference.child(key).setValue(task)
//            }
//        }
//        fun updateTask(task: Task){
//            mReference.child(task.id).setValue(task)
//        }
//        fun reorder(task: Task,from: Int,  to: Int){
//
//            val hour: Int = to % 24
//            val fromDay: Int = Math.floor(from.toDouble() / 24).toInt()
//            val toDay: Int = Math.floor(to.toDouble() / 24).toInt()
//            Log.d("REORDER", hour.toString())
//            Log.d("REORDER", toDay.toString())
//            task.time = hour
//            task.day = task.day + (toDay - fromDay)
//            updateTask(task)
//        }
//        fun deleteTask(key: String){
//            mReference.child(key).setValue(null)
//        }
//    }
//    init{
//        getTasks()
//    }
//}