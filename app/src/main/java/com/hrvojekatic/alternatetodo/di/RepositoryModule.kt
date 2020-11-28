package com.hrvojekatic.alternatetodo.di

import com.hrvojekatic.alternatetodo.repository.TodoRepository
import com.hrvojekatic.alternatetodo.repository.TodoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class RepositoryModule {

	@Binds
	abstract fun provideTodoRepository(todoRepository: TodoRepositoryImpl): TodoRepository
}