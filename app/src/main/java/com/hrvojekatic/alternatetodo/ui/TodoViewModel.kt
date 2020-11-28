package com.hrvojekatic.alternatetodo.ui

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hrvojekatic.alternatetodo.db.TodoEntity
import com.hrvojekatic.alternatetodo.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel @ViewModelInject constructor(private val todoRepository: TodoRepository, @Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {
	val todoListLiveData = todoRepository.getTodoList()

	fun insert(todo: TodoEntity) = viewModelScope.launch {
		todoRepository.insert(todo)
	}

	fun update(todo: TodoEntity) = viewModelScope.launch {
		todoRepository.update(todo)
	}

	fun delete(todo: TodoEntity) = viewModelScope.launch {
		todoRepository.delete(todo)
	}

	fun deleteByDate(date: String) = viewModelScope.launch {
		todoRepository.deleteByDate(date)
	}
}
