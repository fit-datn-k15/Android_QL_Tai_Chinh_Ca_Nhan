package com.example.quanlythuchi.data.repository.income

import com.example.quanlythuchi.data.room.entity.Income

interface InComeRepository {
    suspend fun getAllIncome() : List<Income>
    suspend fun insertIncome(income: Income) : Long
    suspend fun getIncomeByDate(date : String): List<Income>
}