package com.example.quanlythuchi.view.main.all_expense_income

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Expense
import com.example.quanlythuchi.data.entity.Income
import com.example.quanlythuchi.data.repository.local.category.CategoryRepository
import com.example.quanlythuchi.data.repository.local.expense.ExpenseRepository
import com.example.quanlythuchi.data.repository.local.income.InComeRepository
import com.example.quanlythuchi.view.main.calendar.ExpenseIncome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllIncomeExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: InComeRepository,
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {
    private var listExpense = mutableListOf<Expense>()
    private var listIncome = mutableListOf<Income>()
    var listCategory = mutableListOf<Category>()
    private var mapCategory : Map<String, Category> = mutableMapOf()

    var dataRcv = MutableLiveData(mutableListOf<ExpenseIncome>())
    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            val lExpense = expenseRepository.getAllExpense()
            val lIncome = incomeRepository.getAllIncome()
            val lCategory = categoryRepository.getAll()
            withContext(Dispatchers.Main) {
                listExpense.addAll(lExpense)
                listIncome.addAll(lIncome)
                listCategory.addAll(lCategory)
                mapCategory = lCategory.associateBy { it.idCategory ?: "otherCategory" }
                prepareData()
            }
        }
    }
    fun prepareData( ){
        viewModelScope.launch(Dispatchers.IO) {
            val list: MutableList<ExpenseIncome> = mutableListOf()
            for (item in listExpense) {
                    list.add(
                        ExpenseIncome(
                            idCategory = item.idCategory,
                            id = item.idExpense,
                            idUser = item.idUser,
                            noteExpenseIncome = item.note,
                            date = item.date,
                            money = item.expense,
                            typeExpenseOrIncome = ExpenseIncome.TYPE_EXPENSE,
                            titleCategory = mapCategory[item.idCategory]?.title,
                            icon = mapCategory[item.idCategory]?.icon
                        )
                    )
            }
            for (item in listIncome) {
                    list.add(
                        ExpenseIncome(
                            idCategory = item.idCategory,
                            id = item.idIncome,
                            idUser = item.idUser,
                            noteExpenseIncome = item.note,
                            date = item.date,
                            money = item.income,
                            typeExpenseOrIncome = ExpenseIncome.TYPE_INCOME,
                            titleCategory = mapCategory[item.idCategory]?.title,
                            icon = mapCategory[item.idCategory]?.icon
                        )
                    )
                }

            withContext(Dispatchers.Main) {
                dataRcv.postValue(list)
            }
        }
    }
}