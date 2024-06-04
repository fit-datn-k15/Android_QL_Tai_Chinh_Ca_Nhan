package com.example.quanlythuchi.view.edit_expense_income

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.SingleLiveData
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Expense
import com.example.quanlythuchi.data.entity.Income
import com.example.quanlythuchi.data.repository.local.expense.ExpenseRepository
import com.example.quanlythuchi.data.repository.local.income.InComeRepository
import com.example.quanlythuchi.extension.isNotNullAndNotEmpty
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.E

@HiltViewModel
class EditExpenseIncomeViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: InComeRepository
) : BaseViewModel() {
    var isEnableButtonAdd = MutableLiveData(false)
    var money = ""
        set(value) {
            field = value
            validData()
        }
    var date = LocalDate.now()
        set(value) {
            field = value
            validData()
        }
    var note = ""
        set(value) {
            field = value
            validData()
        }
    var categorySelected : Category? = null
        set(value) {
            field = value
            validData()
        }

    private fun validData() {
        try {
            val numberIncome = money.toLong()
            if (categorySelected != null && money.isNotNullAndNotEmpty() && numberIncome > 0) {
                isEnableButtonAdd.postValue(true)
                return
            }
        }
        catch (_ : Exception) {}
        isEnableButtonAdd.postValue(false)
    }
    val isUpdate = SingleLiveData(false)
    fun updateData(typeUpdate: Int) {
        if (typeUpdate == FragmentEditExpenseIncome.UPDATE_EXPENSE)
            updateExpense()
        else
            updateIncome()
    }
    private fun updateExpense() {
        val item = Expense(
            expense = money.toLong(),
            date = date.toString(),
            note = note,
            idCategory = categorySelected?.idCategory
        )
        viewModelScope.launch(Dispatchers.IO) {
            val isU = expenseRepository.updateExpense(item)
            withContext(Dispatchers.Main) {
                isUpdate.postValue(isU)
            }
        }
    }
    private fun updateIncome() {
        val item = Income(
            income = money.toLong(),
            date = date.toString(),
            note = note,
            idCategory = categorySelected?.idCategory
        )
        viewModelScope.launch(Dispatchers.IO) {
            val isU = incomeRepository.updateIncome(item)
            withContext(Dispatchers.Main) {
                isUpdate.postValue(isU)
            }
        }
    }
}