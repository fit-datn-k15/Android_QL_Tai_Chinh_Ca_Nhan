package com.example.quanlythuchi.view.main.calendar

import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.SingleLiveData
import com.example.quanlythuchi.data.repository.local.expense.ExpenseRepository
import com.example.quanlythuchi.data.repository.local.income.InComeRepository
import com.example.quanlythuchi.data.entity.Expense
import com.example.quanlythuchi.data.entity.Income
import com.example.quanlythuchi.extension.formatMonth
import com.example.quanlythuchi.extension.toLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: InComeRepository
): BaseViewModel() {
    var date = LocalDate.now();
    var selectedDate: LocalDate? = null
    var total = 0L;
    var incomeTotal =0L;
    var expenseTotal =0L;
    var listExpense = mutableListOf<Expense>()
    var listIncome = mutableListOf<Income>()
    var isGetDataByMonth = SingleLiveData(false)

    var listGroupExpenseToShowDayView  : Map<LocalDate, List<Expense>> = mapOf()
    var listGroupIncomeToShowDayView : Map<LocalDate,List<Income>> = mapOf()
    fun getDataByDate() {
        resetData()
        viewModelScope.launch(Dispatchers.IO) {
            val lExpense = expenseRepository.getAllExpense()
            val lIncome = incomeRepository.getAllIncome()
            withContext(Dispatchers.Main) {
                listExpense.clear()
                listIncome.clear()
                listExpense.addAll(lExpense)
                listIncome.addAll(lIncome)
                listGroupExpenseToShowDayView = listExpense.groupBy { it.date.toLocalDate() }
                listGroupIncomeToShowDayView = listIncome.groupBy { it.date.toLocalDate() }
                isGetDataByMonth.postValue(true)
                calculator()
            }
        }
    }
    private fun calculator() {
        for (income in listIncome) {
            income.income?.let { incomeTotal += it }
        }
        for (expense in listExpense)
            expense.expense?.let { expenseTotal += it }
        total =  expenseTotal +  incomeTotal
    }
    fun resetData() {
        incomeTotal = 0L
        expenseTotal = 0L
        total = 0L
        listExpense.clear()
        listIncome.clear()
    }
}