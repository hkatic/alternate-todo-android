package com.hrvojekatic.alternatetodo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hrvojekatic.alternatetodo.R
import com.hrvojekatic.alternatetodo.db.TodoEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class TodoListAdapter internal constructor(context: Context, private val onItemClickListener: (TodoEntity) -> Unit) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
	private val inflater: LayoutInflater = LayoutInflater.from(context)
	private var todos = emptyList<TodoEntity>()

	inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val todosItemView: TextView = itemView.findViewById(R.id.text_view)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
		val itemView = inflater.inflate(R.layout.list_item, parent, false)
		return TodoViewHolder(itemView)
	}

	override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
		val currentTodoEntity = todos[position]
		val localDateTime = LocalDateTime.parse(currentTodoEntity.date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
		val formattedDateTime = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT).withLocale(Locale.getDefault())
		val humanizedDateTime = localDateTime.format(formattedDateTime)
		holder.todosItemView.text = "$humanizedDateTime | ${currentTodoEntity.title}"
		holder.todosItemView.setOnClickListener { onItemClickListener(currentTodoEntity) }
	}

	internal fun setTodos(todos: List<TodoEntity>) {
		this.todos = todos
		notifyDataSetChanged()
	}

	override fun getItemCount() = todos.size
}
