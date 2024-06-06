package com.example.quanlythuchi.view.edit_expense_income

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.SingleLiveData
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Expense
import com.example.quanlythuchi.data.entity.Income
import com.example.quanlythuchi.data.repository.local.expense.ExpenseRepository
import com.example.quanlythuchi.data.repository.local.income.InComeRepository
import com.example.quanlythuchi.extension.isNotNullAndNotEmpty
import com.example.quanlythuchi.view.main.calendar.ExpenseIncome
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.E

@HiltViewModel
class EditExpenseIncomeViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val expenseRepository: ExpenseRepository,
    private val incomeRepository: InComeRepository
) : BaseViewModel() {
    var itemData : ExpenseIncome? = null
    var isEnableButtonAdd = MutableLiveData(false)
    var listCategory : MutableList<Category>? = null
    var itemCategorySelected = -1
    var money = ""
        set(value) {
            field = value
            validData()
            val a = intArrayOf()
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
            if (categorySelected != null
                && money.isNotNullAndNotEmpty()
                && numberIncome > 0
                && (itemData?.noteExpenseIncome != note
                        || itemData?.date.toString() != date.toString()
                        || itemData?.money != money.toLong()
                        || itemData?.idCategory != categorySelected?.idCategory)
            ) {
                isEnableButtonAdd.postValue(true)
                return
            }
        }
        catch (_ : Exception) {}
        isEnableButtonAdd.postValue(false)
    }
    fun updateItemData(typeUpdate: Int, callBack : (String) -> Unit) {
        if (typeUpdate == ExpenseIncome.TYPE_EXPENSE)
            updateExpense(callBack)
        else
            updateIncome(callBack)
    }
    private fun updateExpense(callBackIsUpdate : (String) -> Unit) {
        val item = Expense(
            idExpense = this.itemData?.id,
            idUser = this.itemData?.idUser,
            expense = money.toLong(),
            date = date.toString(),
            note = note,
            idCategory = categorySelected?.idCategory
        )
        viewModelScope.launch(Dispatchers.IO) {
            val isU = expenseRepository.updateExpense(item)
            withContext(Dispatchers.Main) {
                if (isU) {
                    callBackIsUpdate.invoke(context.getString(R.string.message_update_expense))
                }
                else {
                    callBackIsUpdate.invoke(context.getString(R.string.err_update_income))
                }
            }
        }
    }
    private fun updateIncome(callBackIsUpdate: (String) -> Unit) {
        val item = Income(
            idIncome = this.itemData?.id,
            idUser = this.itemData?.idUser,
            income = money.toLong(),
            date = date.toString(),
            note = note,
            idCategory = categorySelected?.idCategory
        )
        viewModelScope.launch(Dispatchers.IO) {
            val isU = incomeRepository.updateIncome(item)
            withContext(Dispatchers.Main) {
                if (isU) {
                    callBackIsUpdate.invoke(context.getString(R.string.message_update_income))
                }
                else {
                    callBackIsUpdate.invoke(context.getString(R.string.err_update_income))
                }
            }
        }
    }
}