package com.example.quanlythuchi.view.main.calendar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.SingleLiveData
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.repository.local.expense.ExpenseRepository
import com.example.quanlythuchi.data.repository.local.income.InComeRepository
import com.example.quanlythuchi.data.entity.Expense
import com.example.quanlythuchi.data.entity.Income
import com.example.quanlythuchi.data.repository.local.category.CategoryRepository
import com.example.quanlythuchi.extension.toMonthYearString
import com.example.quanlythuchi.extension.toLocalDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: InComeRepository,
    private val categoryRepository: CategoryRepository
): BaseViewModel() {
    var date = LocalDate.now();
    var selectedDate: LocalDate? = null
    var incomeTotal = MutableLiveData(0L);
    var expenseTotal = MutableLiveData(0L);
    var listExpense = mutableListOf<Expense>()
    var listIncome = mutableListOf<Income>()
    var isGetDataByMonth = SingleLiveData(false)

    var listGroupExpenseToShowDayView  : Map<LocalDate, List<Expense>> = mapOf()
    var listGroupIncomeToShowDayView : Map<LocalDate,List<Income>> = mapOf()

    var listCategory : Map<String, Category> = mutableMapOf()
    // list này là tổng hợp các khoản thu, chi của 1 ngày hoặc 1 tháng,
    var listSyntheticByDate = MutableLiveData(mutableListOf<ExpenseIncome>())
    fun getDataByDate() {
        viewModelScope.launch(Dispatchers.IO) {
            val lExpense = expenseRepository.getAllExpense()
            val lIncome = incomeRepository.getAllIncome()
            val lCategory = categoryRepository.getAll()
            withContext(Dispatchers.Main) {
                resetData()
                listExpense.addAll(lExpense)
                listIncome.addAll(lIncome)
                listCategory = lCategory.associateBy { it.idCategory ?: "otherCategory" }
                listGroupExpenseToShowDayView = listExpense.groupBy { it.date.toLocalDate() }
                listGroupIncomeToShowDayView = listIncome.groupBy { it.date.toLocalDate() }
                isGetDataByMonth.postValue(true)
                filterListSyntheticByMonth(YearMonth.now())
                //calculator()
            }
        }
    }
    fun filterListSyntheticByDate(dateSelecting : LocalDate) {
        viewModelScope.launch(Dispatchers.IO) {
            val list: MutableList<ExpenseIncome> = mutableListOf()
            for (item in listExpense) {
                if (dateSelecting.toString() == item.date) {
                    list.add(
                        ExpenseIncome(
                            idCategory = item.idCategory,
                            id = item.idExpense,
                            noteExpenseIncome = item.note,
                            date = item.date,
                            money = item.expense,
                            typeExpenseOrIncome = ExpenseIncome.TYPE_EXPENSE,
                            titleCategory = listCategory[item.idCategory]?.title,
                            icon = listCategory[item.idCategory]?.icon
                        )
                    )
                }
            }
            for (item in listIncome) {
                if (dateSelecting.toString() == item.date) {
                    list.add(
                        ExpenseIncome(
                            idCategory = item.idCategory,
                            id = item.idIncome,
                            noteExpenseIncome = item.note,
                            date = item.date,
                            money = item.income,
                            typeExpenseOrIncome = ExpenseIncome.TYPE_INCOME,
                            titleCategory = listCategory[item.idCategory]?.title,
                            icon = listCategory[item.idCategory]?.icon
                        )
                    )
                }
            }
            withContext(Dispatchers.Main) {
                listSyntheticByDate.postValue(list)
            }
        }
    }
    fun filterListSyntheticByMonth(monthSelecting : YearMonth) {
     //   clearDataTotal()
        var incomeTotalByDate = 0L
        var expenseTotalByDate = 0L
        viewModelScope.launch(Dispatchers.IO) {
            val list: MutableList<ExpenseIncome> = mutableListOf()
            for (item in listExpense) {
                if (monthSelecting.toString() == item.date.toLocalDate().toMonthYearString()) {
                    list.add(
                        ExpenseIncome(
                            idCategory = item.idCategory,
                            id = item.idExpense,
                            noteExpenseIncome = item.note,
                            date = item.date,
                            money = item.expense,
                            typeExpenseOrIncome = ExpenseIncome.TYPE_EXPENSE,
                            titleCategory = listCategory[item.idCategory]?.title,
                            icon = listCategory[item.idCategory]?.icon
                        )
                    )
                    item.expense?.let { expenseTotalByDate += it }
                }
            }
            for (item in listIncome) {
                if (monthSelecting.toString() == item.date.toLocalDate().toMonthYearString()) {
                    list.add(
                        ExpenseIncome(
                            idCategory = item.idCategory,
                            id = item.idIncome,
                            noteExpenseIncome = item.note,
                            date = item.date,
                            money = item.income,
                            typeExpenseOrIncome = ExpenseIncome.TYPE_INCOME,
                            titleCategory = listCategory[item.idCategory]?.title,
                            icon = listCategory[item.idCategory]?.icon
                        )
                    )
                    item.income?.let { incomeTotalByDate += it }
                }
            }
            withContext(Dispatchers.Main) {
                listSyntheticByDate.postValue(list)
                incomeTotal.postValue(incomeTotalByDate)
                expenseTotal.postValue(expenseTotalByDate)
            }
        }
    }
//    private fun calculator() {
//        for (income in listIncome) {
//            income.income?.let { incomeTotal += it }
//        }
//        for (expense in listExpense)
//            expense.expense?.let { expenseTotal += it }
//        total =  expenseTotal +  incomeTotal
//    }
    fun resetData() {
        incomeTotal.postValue(0L)
        expenseTotal.postValue(0L)
        listExpense.clear()
        listIncome.clear()
    }
    private fun clearDataTotal () {
        incomeTotal.postValue(0L)
        expenseTotal.postValue(0L)
    }
}