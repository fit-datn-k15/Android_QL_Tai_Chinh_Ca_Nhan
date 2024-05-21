package com.example.quanlythuchi.data.repository.local.expense

import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Expense

interface ExpenseRepository {
    suspend fun getAllExpense() : MutableList<Expense>
    suspend fun insertExpense(expense: Expense) : Boolean
    suspend fun getExpenseByDay(date: String) : List<Expense>
    suspend fun getExpenseByWeek(week: String) : List<Expense>
    suspend fun getExpenseByMonth(month: String) : List<Expense>
}