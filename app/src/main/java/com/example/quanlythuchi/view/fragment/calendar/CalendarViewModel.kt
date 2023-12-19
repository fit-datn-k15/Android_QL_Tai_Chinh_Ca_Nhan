package com.example.quanlythuchi.view.fragment.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.SingleLiveData
import com.example.quanlythuchi.data.repository.expense.ExpenseRepository
import com.example.quanlythuchi.data.repository.income.InComeRepository
import com.example.quanlythuchi.data.room.entity.Expense
import com.example.quanlythuchi.data.room.entity.Income
import com.example.quanlythuchi.extension.formatDateTime
import com.example.quanlythuchi.extension.formatMoney
import com.google.type.Money
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.checkerframework.checker.units.qual.m
import java.text.NumberFormat
import java.time.LocalDate
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: InComeRepository
): BaseViewModel() {
    var date =LocalDate.now();
    var total = "";
    var incomeTotal ="";
    var expenseTotal ="";
    var listExpense = ArrayList<Expense>()
    var listIncome = ArrayList<Income>()
    var isGetDataByDate = SingleLiveData(false)
    fun getDataByDate() {
        resetData()
        viewModelScope.launch(Dispatchers.IO) {
            listExpense.addAll(expenseRepository.getExpenseByDate(date.formatDateTime()))
            listIncome.addAll(incomeRepository.getIncomeByDate(date.formatDateTime()))
            withContext(Dispatchers.Main) {
                isGetDataByDate.postValue(true)
                calculator()
            }
        }
    }
    private fun calculator() {
        var i = 0L;
        var e = 0L;
        var sum = 0L
        for (income in listIncome) {
            income.income?.let { i += it }
        }
        for (expense in listExpense)
            expense.expense?.let { e += it }
        total =(i+e).formatMoney()
        incomeTotal = i.formatMoney()
        expenseTotal = e.formatMoney()
    }
     fun resetData() {
        incomeTotal = ""
        expenseTotal =""
        total = ""
        listExpense.clear()
        listIncome.clear()
    }
}