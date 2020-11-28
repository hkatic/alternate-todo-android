package com.hrvojekatic.alternatetodo.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hrvojekatic.alternatetodo.databinding.ActivityMainBinding
import com.hrvojekatic.alternatetodo.db.TodoEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	private lateinit var binding: ActivityMainBinding
	private val todoViewModel by viewModels<TodoViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		val adapter = TodoListAdapter(this) { todoEntity -> viewDetailsForTask(todoEntity) }
		binding.recyclerView.adapter = adapter
		binding.recyclerView.layoutManager = LinearLayoutManager(this)
		todoViewModel.todoListLiveData.observe(this, { todoList ->
			todoList?.let { adapter.setTodos(it) }
		})
		binding.fab.setOnClickListener { addNewTask() }
	}

	private fun viewDetailsForTask(todoEntity: TodoEntity) {
		val intent = Intent(this, TaskDetailActivity::class.java).apply {
			putExtra("taskDate", todoEntity.date)
			putExtra("taskTitle", todoEntity.title)
			putExtra("taskText", todoEntity.text)
		}
		startActivity(intent)
	}

	private fun addNewTask() {
		val intent = Intent(this, EditTaskActivity::class.java)
		startActivity(intent)
	}
}