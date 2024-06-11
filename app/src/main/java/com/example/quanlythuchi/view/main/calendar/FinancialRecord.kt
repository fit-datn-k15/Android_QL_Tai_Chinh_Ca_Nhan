package com.example.quanlythuchi.view.main.calendar

import android.os.Parcelable
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Expense
import com.example.quanlythuchi.data.entity.Income
import kotlinx.parcelize.Parcelize

@Parcelize
data class FinancialRecord (
    val id : String?,
    val idCategory : String? = null,
    val idUser : String? = null,
    val date : String?,
    val typeExpenseOrIncome: Int,
    val money : Long? = null,
    val icon : String? = null,
    val noteExpenseIncome : String? = null,
    val titleCategory : String? = null
) :Parcelable {
    companion object {
        const val TYPE_EXPENSE = 0
        const val TYPE_INCOME = 1
    }
}

fun Expense.toExpenseIncome(category: Category?) : FinancialRecord {
    return FinancialRecord(
        id = id,
        idCategory = idCategory,
        idUser = idUser,
        date = date,
        typeExpenseOrIncome = FinancialRecord.TYPE_EXPENSE,
        money = money,
        icon = category?.icon ,
        noteExpenseIncome = this.note,
        titleCategory = category?.title,
    )
}
fun Income.toExpenseIncome(category: Category?) : FinancialRecord {
    return FinancialRecord(
        id = id,
        idCategory = idCategory,
        idUser = idUser,
        date = date,
        typeExpenseOrIncome = FinancialRecord.TYPE_INCOME,
        money = money,
        icon = category?.icon ,
        noteExpenseIncome = this.note,
        titleCategory = category?.title
    )
}

