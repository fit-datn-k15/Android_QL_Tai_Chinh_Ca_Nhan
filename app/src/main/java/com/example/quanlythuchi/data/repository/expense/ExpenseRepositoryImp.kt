package com.example.quanlythuchi.data.repository.expense

import com.example.quanlythuchi.base.Constance
import com.example.quanlythuchi.data.room.dao.CategoryDao
import com.example.quanlythuchi.data.room.dao.ExpenseDao
import com.example.quanlythuchi.data.room.entity.Category
import com.example.quanlythuchi.data.room.entity.Expense
import javax.inject.Inject

class ExpenseRepositoryImp @Inject constructor(
    private val expenseDao: ExpenseDao
): ExpenseRepository {

    override suspend fun getAllExpense(): List<Expense> {
        return expenseDao.getAllExpense()
    }

    override suspend fun insertExpense(expense: Expense): Long {
        return expenseDao.insertExpense(expense)
    }

    override suspend fun getExpenseByDate(date: Long): List<Expense> {
       return expenseDao.getExpenseByDate(date)
    }
}