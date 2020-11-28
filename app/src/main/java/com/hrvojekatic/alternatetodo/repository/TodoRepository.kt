package com.hrvojekatic.alternatetodo.repository

import androidx.lifecycle.LiveData
import com.hrvojekatic.alternatetodo.db.TodoEntity

interface TodoRepository {
	fun getTodoList(): LiveData<List<TodoEntity>>
	suspend fun insert(todo: TodoEntity)
	suspend fun update(todo: TodoEntity)
	suspend fun delete(todo: TodoEntity)
	suspend fun deleteByDate(date: String)
}