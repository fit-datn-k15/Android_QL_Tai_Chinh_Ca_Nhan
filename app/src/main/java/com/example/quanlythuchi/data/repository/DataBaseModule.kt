package com.example.quanlythuchi.data.repository

import android.content.Context
import com.example.quanlythuchi.data.room.AppDatabase
import com.example.quanlythuchi.data.room.dao.CategoryDao
import com.example.quanlythuchi.data.room.dao.ExpenseDao
import com.example.quanlythuchi.data.room.dao.IncomeDao
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
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return  AppDatabase.getInstance(context)
    }
    @Provides
    @Singleton
    fun provideCategoryDao(db: AppDatabase): CategoryDao {
        return db.categoryDao()
    }
    @Provides
    @Singleton
    fun provideExpenseDao(db: AppDatabase): ExpenseDao {
        return db.expenseDao()
    }
    @Provides
    @Singleton
    fun provideIncomeDao(db : AppDatabase) : IncomeDao {
        return db.incomeDao()
    }
}