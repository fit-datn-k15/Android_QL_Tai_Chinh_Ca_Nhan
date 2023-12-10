package com.example.quanlythuchi.data.repository

import android.content.Context
import com.example.quanlythuchi.data.room.AppDatabase
import com.example.quanlythuchi.data.room.dao.CategoryDao
import com.example.quanlythuchi.data.room.dao.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {
    @Provides
    @Singleton
    fun provideCategoryDao(@ApplicationContext context : Context): CategoryDao {
        return AppDatabase.getInstance(context = context).categoryDao()
    }
    @Provides
    @Singleton
    fun provideExpenseDao(@ApplicationContext context : Context): ExpenseDao {
        return AppDatabase.getInstance(context = context).expenseDao()
    }
}