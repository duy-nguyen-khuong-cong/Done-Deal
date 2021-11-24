//package com.example.myapplication.controllers
//
//import com.example.myapplication.models.Task
//import com.example.myapplication.models.TaskJSONStore
//import com.example.myapplication.views.TaskView
//import mu.KotlinLogging
//
//class TaskController {
//
//    val tasks = TaskJSONStore()
//    val taskView = TaskView()
//    val logger = KotlinLogging.logger {}
//
//    init {
//        logger.info { "Launching Task Console App" }
//        println("Task Kotlin App Version 3.0")
//    }
//
//    fun start() {
//        var input: Int
//
//        do {
//            input = menu()
//            when (input) {
//                1 -> add()
//                2 -> update()
//                3 -> list()
//                4 -> search()
//                5 -> delete()
//                -99 -> dummyData()
//                -1 -> println("Exiting App")
//                else -> println("Invalid Option")
//            }
//            println()
//        } while (input != -1)
//        logger.info { "Shutting Down Task Console App" }
//    }
//
//    fun menu() :Int { return taskView.menu() }
//
//    fun add(){
//        var aTask = Task()
//
//        if (taskView.addTaskData(aTask))
//            tasks.create(aTask)
//        else
//            logger.info("Task Not Added")
//    }
//    fun delete() {
//        taskView.listTasks(tasks)
//        var searchId = taskView.getId()
//        val aTask = search(searchId)
//
//        if(aTask != null) {
//            tasks.delete(aTask)
//            println("Task Deleted...")
//            taskView.listTasks(tasks)
//        }
//        else
//            println("Task Not Deleted...")
//    }
//    fun list() {
//        taskView.listTasks(tasks)
//    }
//
//    fun update() {
//
//        taskView.listTasks(tasks)
//        var searchId = taskView.getId()
//        val aTask = search(searchId)
//
//        if(aTask != null) {
//            if(taskView.updateTaskData(aTask)) {
//                tasks.update(aTask)
//                taskView.showTask(aTask)
//                logger.info("Task Updated : [ $aTask ]")
//            }
//            else
//                logger.info("Task Not Updated")
//        }
//        else
//            println("Task Not Updated...")
//    }
//
//    fun search() {
//        val aTask = search(taskView.getId())!!
//        taskView.showTask(aTask)
//    }
//
//
//    fun search(id: Long) : Task? {
//        var foundTask = tasks.findOne(id)
//        return foundTask
//    }
//
//    fun dummyData() {
//        tasks.create(Task(title = "New York New York", description = "So Good They Named It Twice"))
//        tasks.create(Task(title= "Ring of Kerry", description = "Some place in the Kingdom"))
//        tasks.create(Task(title = "Waterford City", description = "You get great Blaas Here!!"))
//    }
//}
