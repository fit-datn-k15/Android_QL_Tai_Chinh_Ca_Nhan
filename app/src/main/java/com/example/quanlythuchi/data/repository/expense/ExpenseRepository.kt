package com.example.quanlythuchi.data.repository.expense

import com.example.quanlythuchi.data.room.entity.Category
import com.example.quanlythuchi.data.room.entity.Expense

interface ExpenseRepository {
    suspend fun getAllExpense() : List<Expense>
    suspend fun insertExpense(expense: Expense) : Long
}