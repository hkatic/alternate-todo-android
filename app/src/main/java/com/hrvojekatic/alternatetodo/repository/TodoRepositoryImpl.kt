package com.hrvojekatic.alternatetodo.repository

import androidx.lifecycle.LiveData
import com.hrvojekatic.alternatetodo.db.TodoDao
import com.hrvojekatic.alternatetodo.db.TodoEntity
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val todoDao: TodoDao) : TodoRepository {

	override fun getTodoList(): LiveData<List<TodoEntity>> {
		return todoDao.getTodos()
	}

	override suspend fun insert(todo: TodoEntity) {
		todoDao.insert(todo)
	}

	override suspend fun update(todo: TodoEntity) {
		todoDao.update(todo)
	}

	override suspend fun delete(todo: TodoEntity) {
		todoDao.delete(todo)
	}

	override suspend fun deleteByDate(date: String) {
		todoDao.deleteByDate(date)
	}
}
