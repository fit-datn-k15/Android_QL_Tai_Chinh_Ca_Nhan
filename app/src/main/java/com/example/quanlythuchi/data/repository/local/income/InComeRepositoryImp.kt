package com.example.quanlythuchi.data.repository.local.income

import com.example.quanlythuchi.data.entity.Income
import javax.inject.Inject

class InComeRepositoryImp @Inject constructor(

) : InComeRepository{
    override suspend fun getAllIncome():List<Income>{
        return listOf()
    }

    override suspend fun insertIncome(income: Income): Long {
        return 1
    }

    override suspend fun getIncomeByDate(date: String): List<Income> {
        return listOf()
    }
}