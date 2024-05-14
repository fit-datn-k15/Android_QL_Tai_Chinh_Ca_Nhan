package com.example.quanlythuchi.data.repository.local.expense

import com.example.quanlythuchi.data.entity.Expense

interface ExpenseRepository {
    suspend fun getAllExpense() : List<Expense>
    suspend fun insertExpense(expense: Expense) : Long
    suspend fun getExpenseByDate(date: String) : List<Expense>
}