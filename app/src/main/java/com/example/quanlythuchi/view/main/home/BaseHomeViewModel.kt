package com.example.quanlythuchi.view.main.home

import androidx.lifecycle.MutableLiveData
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.SingleLiveData
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.extension.isNotNullAndNotEmpty
import java.time.LocalDate

abstract class BaseHomeViewModel : BaseViewModel() {
    var typeCurrentFragment : Int = FragmentHome.FRAGMENT_EXPENSE
    var isEnableButtonAddAtToolbar = MutableLiveData(false)

    var isEnableButtonAddExpense = MutableLiveData(false)
    var listCategoryExpense = SingleLiveData<MutableList<Category>>(mutableListOf())
    var itemCategoryExpenseSelected = -1
    var dateExpense: LocalDate = LocalDate.now()
    var categoryExpenseSelected : Category? = null
        set(value) {
            field = value
            checkValidDataExpense()
        }
    var noteExpense : String = ""
        set(value) {
            field = value
            checkValidDataExpense()
            checkValidDataIncome()
        }
    var moneyExpense : String = ""
        set(value) {
            field = value
            checkValidDataExpense()
            checkValidDataIncome()
        }
    fun checkValidDataExpense() {
        try {
            val numberExpense = moneyExpense.toLong()
            if (categoryExpenseSelected != null && noteExpense.isNotNullAndNotEmpty()  && numberExpense > 0) {
                isEnableButtonAddExpense.postValue(true)
                return
            }
        }
        catch (_ : Exception) {}
        isEnableButtonAddExpense.postValue(false)
    }



    var isEnableButtonAddIncome = MutableLiveData(false)
    var itemCategoryIncomeSelected = -1
    var listCategoryIncome = MutableLiveData<MutableList<Category>>(mutableListOf())
    var dateIncome: LocalDate = LocalDate.now()
    var noteIncome : String = ""
        set(value) {
            field = value
            checkValidDataExpense()
            checkValidDataIncome()
        }
    var moneyIncome : String = ""
        set(value) {
            field = value
            checkValidDataExpense()
            checkValidDataIncome()
        }
    var categoryIncomeSelected : Category? = null
        set(value) {
            field = value
            checkValidDataIncome()
        }
    fun checkValidDataIncome() {
        try {
            val numberIncome = moneyIncome.toLong()
            if (categoryIncomeSelected != null && noteIncome.isNotNullAndNotEmpty()  && numberIncome > 0) {
                isEnableButtonAddIncome.postValue(true)
                return
            }
        }
        catch (_ : Exception) {}
        isEnableButtonAddIncome.postValue(false)
    }

}