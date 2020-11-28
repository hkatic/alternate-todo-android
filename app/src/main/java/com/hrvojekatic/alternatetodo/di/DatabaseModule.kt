package com.hrvojekatic.alternatetodo.di

import android.content.Context
import androidx.room.Room
import com.hrvojekatic.alternatetodo.db.AppDatabase
import com.hrvojekatic.alternatetodo.db.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

	@Provides
	fun provideTodoDao(appDatabase: AppDatabase): TodoDao {
		return appDatabase.todoDao()
	}

	@Singleton
	@Provides
	fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
		return Room.databaseBuilder(appContext, AppDatabase::class.java, "alternate_todo.db").build()
	}
}