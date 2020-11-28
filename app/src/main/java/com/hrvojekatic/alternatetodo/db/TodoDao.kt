package com.hrvojekatic.alternatetodo.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDao {
	@Query("SELECT * FROM todo_table ORDER BY date ASC")
	fun getTodos(): LiveData<List<TodoEntity>>

	@Insert
	suspend fun insert(todo: TodoEntity)

	@Delete
	suspend fun delete(todo: TodoEntity)

	@Update
	suspend fun update(todo: TodoEntity)

	@Query("DELETE FROM todo_table WHERE date = :date")
	suspend fun deleteByDate(date: String)
}