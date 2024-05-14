package com.example.quanlythuchi.data.repository.local.income

import com.example.quanlythuchi.data.entity.Income

interface InComeRepository {
    suspend fun getAllIncome() : List<Income>
    suspend fun insertIncome(income: Income) : Long
    suspend fun getIncomeByDate(date : String): List<Income>
}