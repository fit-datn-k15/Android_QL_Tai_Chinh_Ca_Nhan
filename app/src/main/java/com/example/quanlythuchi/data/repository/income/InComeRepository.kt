package com.example.quanlythuchi.data.repository.income

import com.example.quanlythuchi.data.room.entity.Category
import com.example.quanlythuchi.data.room.entity.Expense
import com.example.quanlythuchi.data.room.entity.Income

interface InComeRepository {
    fun getAllIncome() : List<Income>
    suspend fun insertExpense(income: Income) : Long
}