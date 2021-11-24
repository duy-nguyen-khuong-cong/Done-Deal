package com.example.myapplication.models

interface TaskInterface {
    fun findAll(): List<Task>
    fun findOne(id: Long): Task?
    fun create(task: Task)
    fun update(task: Task)
    fun delete(task: Task)
}