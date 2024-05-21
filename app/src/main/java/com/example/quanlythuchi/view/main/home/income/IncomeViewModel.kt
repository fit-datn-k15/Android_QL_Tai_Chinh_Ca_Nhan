package com.example.quanlythuchi.view.main.home.income

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.Constant
import com.example.quanlythuchi.base.SingleLiveData
import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.data.repository.local.category.CategoryRepository
import com.example.quanlythuchi.data.repository.local.income.InComeRepository
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Income
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
    var listCategoryIncome = MutableLiveData<MutableList<Category>>()
    var idItemRcvCategorySelect = -1


    var date = LocalDate.now()
    var categoryIncomeSelected : Category? = null
    var note: String? =""
    var income : String?= ""

    var isAddIncome = SingleLiveData(false)
    fun getCategoryIncome() {
        viewModelScope.launch(Dispatchers.IO) {
            val it = categoryRepository.getAllCategory(Fb.CategoryIncome)
            withContext(Dispatchers.Main) {
                it.add(Category.categoryAdded())
                listCategoryIncome.postValue(it)
            }
        }
    }
    fun submitIncome() {
        val a = income
        val income = Income(
            idCategory = categoryIncomeSelected?.idCategory,
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