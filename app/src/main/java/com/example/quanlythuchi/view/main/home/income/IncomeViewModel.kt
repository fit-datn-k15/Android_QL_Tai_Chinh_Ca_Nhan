package com.example.quanlythuchi.view.main.home.income

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.Constant
import com.example.quanlythuchi.base.SingleLiveData
import com.example.quanlythuchi.data.repository.category.CategoryRepository
import com.example.quanlythuchi.data.repository.income.InComeRepository
import com.example.quanlythuchi.data.room.entity.Category
import com.example.quanlythuchi.data.room.entity.Income
import com.example.quanlythuchi.extension.formatDateTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private var categoryRepository: CategoryRepository,
    private var incomeRepository: InComeRepository
) : BaseViewModel() {
    var listCategory : MutableList<Category> = mutableListOf()
    var isCategorySuccess = MutableLiveData(false)
    var idItemRcvCategorySelect = -1


    var date = LocalDate.now()
    var category : Category? = null
    var note: String? =""
    var income : String?= ""

    var isAddIncome = SingleLiveData(false)
    fun getCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            val it = categoryRepository.getAllCategory(Constant.CATEGORY_INCOME)
            withContext(Dispatchers.Main) {
                listCategory.clear()
                listCategory.addAll(it)
                isCategorySuccess.postValue(true)
            }
        }
    }
    fun submitIncome() {
        val a = income
        val income = Income(
            idCategory = category?.idCategory,
            date = this.date.formatDateTime(),
            note = this.note,
            income = this.income?.toLong())
        viewModelScope.launch(Dispatchers.IO) {
            val it = incomeRepository.insertIncome(income)
            withContext(Dispatchers.Main) {
                isAddIncome.postValue(true)
            }
        }
    }

}