package com.example.quanlythuchi.view.main.home

import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.SingleLiveData
import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Expense
import com.example.quanlythuchi.data.entity.Income
import com.example.quanlythuchi.data.repository.local.category.CategoryRepository
import com.example.quanlythuchi.data.repository.local.expense.ExpenseRepository
import com.example.quanlythuchi.data.repository.local.income.InComeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShareHomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: InComeRepository
) : BaseHomeViewModel() {

    var isAddExpense = SingleLiveData(false)
    var isAddIncome = SingleLiveData(false)
    fun getCategoryExpense() {
        viewModelScope.launch(Dispatchers.IO) {
            val it = categoryRepository.getAllCategory(Fb.CategoryExpense)
            withContext(Dispatchers.Main) {
                it.add(Category.categoryAdded())
                listCategoryExpense.postValue(it)
            }
        }

    }
    fun getCategoryIncome() {
        viewModelScope.launch(Dispatchers.IO) {
            val it = categoryRepository.getAllCategory(Fb.CategoryIncome)
            withContext(Dispatchers.Main) {
                it.add(Category.categoryAdded())
                listCategoryIncome.postValue(it)
            }
        }
    }
    fun submitExpense() {
        val expense = Expense(
            idCategory = categoryExpenseSelected?.idCategory,
            date = this.dateExpense.toString(),
            note = this.noteExpense.trim(),
            expense = this.moneyExpense.toLong()
        )
        viewModelScope.launch(Dispatchers.IO) {
            val it = expenseRepository.insertExpense(expense)
            withContext(Dispatchers.Main) {
                isAddExpense.value = true
            }
        }
    }
    fun submitIncome() {
        val income = Income(
            idCategory = categoryIncomeSelected?.idCategory,
            date = this.dateIncome.toString(),
            note = this.noteIncome.trim(),
            income = this.moneyIncome.toLong()
        )
        viewModelScope.launch(Dispatchers.IO) {
            val it = incomeRepository.insertIncome(income)
            withContext(Dispatchers.Main) {
                isAddIncome.value = true
            }
        }
    }
}