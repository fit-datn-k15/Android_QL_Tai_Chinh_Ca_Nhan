package com.example.quanlythuchi.view.main.calendar

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExpenseIncome (
    val id : String?,
    val date : String?,
    val typeExpenseOrIncome: Int,
    val money : Long? = null,
    val icon : String? = null,
    val idCategory : String? = null,
    val noteExpenseIncome : String? = null,
    val titleCategory : String? = null
) :Parcelable {
    companion object {
        const val TYPE_EXPENSE = 0
        const val TYPE_INCOME = 1
    }
}

