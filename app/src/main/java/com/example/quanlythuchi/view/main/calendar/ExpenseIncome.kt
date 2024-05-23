package com.example.quanlythuchi.view.main.calendar

import java.time.LocalDateTime

data class ExpenseIncome (
    val id : String,
    val time : LocalDateTime,
    val typeMoney: Int,
    val money : Long? = null,
    val icon : String? = null,
    val idCategory : String? = null,
    val noteExpenseIncome : String? = null,
    val titleCategory : String? = null
) {
    companion object {
        const val TYPE_EXPENSE = 0
        const val TYPE_INCOME = 1
    }
}

