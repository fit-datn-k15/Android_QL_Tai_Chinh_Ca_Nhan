package com.example.quanlythuchi.view.main.calendar

import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.SingleLiveData
import com.example.quanlythuchi.data.repository.local.expense.ExpenseRepository
import com.example.quanlythuchi.data.repository.local.income.InComeRepository
import com.example.quanlythuchi.data.entity.Expense
import com.example.quanlythuchi.data.entity.Income
import com.example.quanlythuchi.extension.formatDateTime
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
    var date =LocalDate.now();
    var total = 0L;
    var incomeTotal =0L;
    var expenseTotal =0L;
    var listExpense = ArrayList<Expense>()
    var listIncome = ArrayList<Income>()
    var isGetDataByDate = SingleLiveData(false)


    var listGroupExpense  : Map<LocalDate, List<Expense>> = mapOf()
    var listGroupIncome : Map<LocalDate,List<Income>> = mapOf()
    fun getDataByDate() {
        resetData()
        viewModelScope.launch(Dispatchers.IO) {
            listExpense.addAll(expenseRepository.getExpenseByDay(date.formatDateTime()))
            listIncome.addAll(incomeRepository.getIncomeByDate(date.formatDateTime()))
            withContext(Dispatchers.Main) {
                listGroupExpense = listExpense.groupBy { it.date!!.toLocalDate() }
                listGroupIncome = listIncome.groupBy { it.date!!.toLocalDate() }
                isGetDataByDate.postValue(true)
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