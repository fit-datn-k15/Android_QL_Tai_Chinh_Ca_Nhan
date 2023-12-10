package com.example.quanlythuchi.view.fragment.home.expense

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.Constance
import com.example.quanlythuchi.data.repository.category.CategoryRepository
import com.example.quanlythuchi.data.room.entity.Category
import com.example.quanlythuchi.data.repository.expense.ExpenseRepository
import com.example.quanlythuchi.data.room.entity.Expense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject
@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {
    var category : MutableList<Category> = mutableListOf()
    var date = LocalDate.now()
    var isCategorySuccess = MutableLiveData(false)
    var idCategorySelect = -1
    fun getCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val it = categoryRepository.getCategoryExpense(Constance.CATEGORY_EXPENSE)
            withContext(Dispatchers.Main) {
                category.clear()
                category.addAll(it)
                isCategorySuccess.postValue(true)
            }
        }
    }
    fun submitExpense() {
//        val expense = Expense(
//            idCategory = Constance.CATEGORY_EXPENSE,
//            date = )
//        viewModelScope.launch(Dispatchers.IO) {
//            val it = expenseRepository.insertExpense()
//        }
    }
}