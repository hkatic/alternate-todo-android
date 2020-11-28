package com.hrvojekatic.alternatetodo.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hrvojekatic.alternatetodo.R
import com.hrvojekatic.alternatetodo.databinding.ActivityEditTaskBinding
import com.hrvojekatic.alternatetodo.db.TodoEntity
import com.hrvojekatic.alternatetodo.util.DatePickerHelper
import com.hrvojekatic.alternatetodo.util.TimePickerHelper
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class EditTaskActivity : AppCompatActivity() {
	private lateinit var binding: ActivityEditTaskBinding
	private val todoViewModel by viewModels<TodoViewModel>()
	private lateinit var datePicker: DatePickerHelper
	private lateinit var timePicker: TimePickerHelper
	private var dateTime = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityEditTaskBinding.inflate(layoutInflater)
		setContentView(binding.root)
		if (intent.extras?.getBoolean("modify", false) == true) {
			binding.editTextDate.setText(intent.extras?.getString("taskDate"))
			binding.editTextTitle.setText(intent.extras?.getString("taskTitle"))
			binding.editTextText.setText(intent.extras?.getString("taskText"))
		}
		datePicker = DatePickerHelper(this, true)
		timePicker = TimePickerHelper(this, is24HourView = true, isSpinnerType = true)
		binding.buttonSave.setOnClickListener { saveTask() }
		binding.buttonPickDateTime.setOnClickListener { pickDateAndTime() }
	}

	private fun pickDateAndTime() {
		val cal = Calendar.getInstance()
		val d = cal.get(Calendar.DAY_OF_MONTH)
		val m = cal.get(Calendar.MONTH)
		val y = cal.get(Calendar.YEAR)
		datePicker.showDialog(d, m, y, object : DatePickerHelper.Callback {
			override fun onDateSelected(dayofMonth: Int, month: Int, year: Int) {
				val dayStr = if (dayofMonth < 10) "0${dayofMonth}" else "$dayofMonth"
				val mon = month + 1
				val monthStr = if (mon < 10) "0${mon}" else "$mon"
				dateTime = "${dayStr}.${monthStr}.${year}"
				pickATime()
			}
		})
	}

	private fun pickATime() {
		val cal = Calendar.getInstance()
		val h = cal.get(Calendar.HOUR_OF_DAY)
		val m = cal.get(Calendar.MINUTE)
		timePicker.showDialog(h, m, object : TimePickerHelper.Callback {
			override fun onTimeSelected(hourOfDay: Int, minute: Int) {
				val hourStr = if (hourOfDay < 10) "0${hourOfDay}" else "$hourOfDay"
				val minuteStr = if (minute < 10) "0${minute}" else {
					"$minute"
				}
				dateTime += " ${hourStr}:${minuteStr}"
				binding.editTextDate.setText(dateTime)
			}
		})
	}

	private fun saveTask() {
		when {
			binding.editTextTitle.text.toString().isBlank() -> {
				Toast.makeText(this, getString(R.string.title_cannot_be_empty), Toast.LENGTH_SHORT).show()
				return
			}
			binding.editTextText.text.toString().isBlank() -> {
				Toast.makeText(this, getString(R.string.text_cannot_be_empty), Toast.LENGTH_SHORT).show()
				return
			}
			binding.editTextDate.text.toString().isBlank() -> {
				Toast.makeText(this, getString(R.string.date_and_time_cannot_be_empty), Toast.LENGTH_SHORT).show()
				return
			}
			binding.editTextDate.text.toString().isNotBlank() -> {
				try {
					LocalDateTime.parse(binding.editTextDate.text.toString(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
				} catch (e: Exception) {
					Toast.makeText(this, getString(R.string.invalid_date_and_time), Toast.LENGTH_SHORT).show()
					return
				}
			}
		}
		val todo = TodoEntity(binding.editTextDate.text.toString(), binding.editTextTitle.text.toString(), binding.editTextText.text.toString())
		if (intent.extras?.getBoolean("modify", false) == true) {
			try {
				todoViewModel.update(todo)
				Toast.makeText(this, getString(R.string.task_updated), Toast.LENGTH_SHORT).show()
			} catch (e: Exception) {
				Toast.makeText(this, getString(R.string.error_updating_task), Toast.LENGTH_SHORT).show()
				return
			}
		} else {
			try {
				todoViewModel.insert(todo)
				Toast.makeText(this, getString(R.string.task_saved), Toast.LENGTH_SHORT).show()
			} catch (e: Exception) {
				Toast.makeText(this, getString(R.string.error_saving_task), Toast.LENGTH_SHORT).show()
				return
			}
		}
		finish()
	}
}