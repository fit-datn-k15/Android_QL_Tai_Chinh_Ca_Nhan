package com.example.quanlythuchi.data.repository.income

import com.example.quanlythuchi.data.room.dao.IncomeDao
import com.example.quanlythuchi.data.room.entity.Expense
import com.example.quanlythuchi.data.room.entity.Income
import javax.inject.Inject

class IncomeRepositoryImp @Inject constructor(
    private val incomeDao : IncomeDao
) : InComeRepository{
    override fun getAllIncome():List<Income>{
        return incomeDao.getAllIncome()
    }

    override suspend fun insertExpense(income: Income): Long {
        return incomeDao.insertIncome(income)
    }
}