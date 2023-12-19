package com.example.quanlythuchi.data.repository.income

import com.example.quanlythuchi.data.room.dao.IncomeDao
import com.example.quanlythuchi.data.room.entity.Income
import javax.inject.Inject

class InComeRepositoryImp @Inject constructor(
    private val incomeDao : IncomeDao
) : InComeRepository{
    override suspend fun getAllIncome():List<Income>{
        return incomeDao.getAllIncome()
    }

    override suspend fun insertIncome(income: Income): Long {
        return incomeDao.insertIncome(income)
    }

    override suspend fun getIncomeByDate(date: String): List<Income> {
        return incomeDao.getAllIncomeByDate(date)
    }
}