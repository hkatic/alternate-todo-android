package com.hrvojekatic.alternatetodo.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hrvojekatic.alternatetodo.R
import com.hrvojekatic.alternatetodo.databinding.ActivityTaskDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

@AndroidEntryPoint
class TaskDetailActivity : AppCompatActivity() {
	private lateinit var binding: ActivityTaskDetailBinding
	private val todoViewModel by viewModels<TodoViewModel>()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityTaskDetailBinding.inflate(layoutInflater)
		setContentView(binding.root)
		val localDateTime = LocalDateTime.parse(intent.extras?.getString("taskDate")
				?: "", DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
		val formattedDateTime = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.FULL, FormatStyle.SHORT).withLocale(Locale.getDefault())
		val humanizedDateTime = localDateTime.format(formattedDateTime)
		binding.textViewDate.text = humanizedDateTime
		binding.textViewTitle.text = intent.extras?.getString("taskTitle") ?: ""
		binding.textViewText.text = intent.extras?.getString("taskText") ?: ""
		binding.buttonEdit.setOnClickListener { editTask() }
		binding.buttonDelete.setOnClickListener { deleteTask() }
	}

	private fun editTask() {
		val intent = Intent(this, EditTaskActivity::class.java).apply {
			putExtra("taskDate", intent.extras?.getString("taskDate") ?: "")
			putExtra("taskTitle", intent.extras?.getString("taskTitle") ?: "")
			putExtra("taskText", intent.extras?.getString("taskText") ?: "")
			putExtra("modify", true)
		}
		startActivity(intent)
		finish()
	}

	private fun deleteTask() {
		try {
			todoViewModel.deleteByDate(intent.extras?.getString("taskDate") ?: "")
			Toast.makeText(this, getString(R.string.task_deleted), Toast.LENGTH_SHORT).show()
		} catch (e: Exception) {
			Toast.makeText(this, getString(R.string.error_deleting_task), Toast.LENGTH_SHORT).show()
		}
		finish()
	}
}