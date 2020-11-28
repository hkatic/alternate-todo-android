package com.hrvojekatic.alternatetodo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class TodoEntity(
		@PrimaryKey var date: String,
		@ColumnInfo(name = "title") var title: String,
		@ColumnInfo(name = "text") var text: String
)