package com.example.quanlythuchi.data.repository.local.income

import com.example.quanlythuchi.data.entity.Income

interface InComeRepository {
    suspend fun getAllIncome() : MutableList<Income>
    suspend fun insertIncome(income: Income) : Boolean
    suspend fun getIncomeByDate(date : String): List<Income>
}