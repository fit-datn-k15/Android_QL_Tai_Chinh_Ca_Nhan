package com.example.quanlythuchi.data.repository.local.expense

import com.example.quanlythuchi.data.entity.Expense
import javax.inject.Inject

class ExpenseRepositoryImp @Inject constructor(

): ExpenseRepository {

    override suspend fun getAllExpense(): List<Expense> {
        return listOf()
    }

    override suspend fun insertExpense(expense: Expense): Long {
        return 1
    }

    override suspend fun getExpenseByDate(date: String): List<Expense> {
       return listOf()
    }
}