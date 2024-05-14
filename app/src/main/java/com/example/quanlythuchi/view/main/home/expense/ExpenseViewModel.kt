package com.example.quanlythuchi.view.main.home.expense

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.Constant
import com.example.quanlythuchi.base.SingleLiveData
import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.data.repository.local.category.CategoryRepository
import com.example.quanlythuchi.data.repository.local.expense.ExpenseRepository
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Expense
import com.example.quanlythuchi.extension.formatDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import java.time.LocalDate
import javax.inject.Inject
@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val expenseRepository: ExpenseRepository
) : BaseViewModel() {
    var listCategory : MutableList<Category> = mutableListOf()
    var isCategorySuccess = MutableLiveData(false)
    var idItemRcvCategorySelect = -1

    var date = LocalDate.now()
    var category : Category? = null
    var note : String? = ""
    var expense : String? = ""

    var isAddExpense = SingleLiveData(false)
        set(value) {
            field = value
        }
    fun getCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val it = categoryRepository.getAllCategory(Fb.CategoryExpense)
            withContext(Dispatchers.Main) {
                listCategory.clear()
                listCategory.addAll(it)
                isCategorySuccess.postValue(true)
            }
        }
    }
    fun submitExpense() {
        val expense = Expense(
            idCategory = category?.idCategory,
            date = this.date.formatDateTime(),
            note = this.note,
            expense = this.expense?.toLong())
        viewModelScope.launch(Dispatchers.IO) {
            val it = expenseRepository.insertExpense(expense)
            withContext(Dispatchers.Main) {
                isAddExpense.value = true
            }
        }
    }
    fun clearData() {

    }
}