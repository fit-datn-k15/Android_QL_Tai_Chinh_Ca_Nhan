package com.example.quanlythuchi.data.repository

import com.example.quanlythuchi.data.repository.category.CategoryRepository
import com.example.quanlythuchi.data.repository.category.CategoryRepositoryImp
import com.example.quanlythuchi.data.repository.expense.ExpenseRepository
import com.example.quanlythuchi.data.repository.expense.ExpenseRepositoryImp
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@dagger.Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideExpenseRepository(expenseRepository: ExpenseRepositoryImp) : ExpenseRepository
    @Binds
    abstract fun provideCategoryRepository(categoryRepositoryImp: CategoryRepositoryImp) : CategoryRepository
}