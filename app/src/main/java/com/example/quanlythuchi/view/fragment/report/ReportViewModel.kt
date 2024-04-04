package com.example.quanlythuchi.view.fragment.report

import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.data.repository.expense.ExpenseRepository
import com.example.quanlythuchi.data.repository.income.InComeRepository
import com.example.quanlythuchi.data.room.entity.Expense
import com.example.quanlythuchi.data.room.entity.Income
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    val inComeRepository: InComeRepository,
    val expenseRepository: ExpenseRepository
) : BaseViewModel() {
    var date: LocalDate = LocalDate.now()

    var listExpense = ArrayList<Expense>()
    var listIncome = ArrayList<Income>()
    var total = 0
    var totalExpense = 0L
    var totalIncome = 0L
    fun getAllIncome() {

    }

    fun getAllExpense() {
    }
}