package com.example.quanlythuchi.data.repository.local.expense

import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Expense

interface ExpenseRepository {
    suspend fun getAllExpense() : MutableList<Expense>
    suspend fun insertExpense(expense: Expense) : Boolean
    suspend fun getExpenseByDate(date: String) : List<Expense>
}