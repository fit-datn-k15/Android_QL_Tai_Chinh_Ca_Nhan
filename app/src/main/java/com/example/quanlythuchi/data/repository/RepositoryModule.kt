package com.example.quanlythuchi.data.repository

import com.example.quanlythuchi.data.repository.local.category.CategoryRepository
import com.example.quanlythuchi.data.repository.local.category.CategoryRepositoryImp
import com.example.quanlythuchi.data.repository.local.expense.ExpenseRepository
import com.example.quanlythuchi.data.repository.local.expense.ExpenseRepositoryImp
import com.example.quanlythuchi.data.repository.local.income.InComeRepository
import com.example.quanlythuchi.data.repository.local.income.InComeRepositoryImp
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
    @Binds
    abstract fun provideIncome(incomeRepositoryImp: InComeRepositoryImp) : InComeRepository
}