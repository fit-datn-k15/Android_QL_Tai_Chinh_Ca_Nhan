package com.example.quanlythuchi.data.entity

import com.example.quanlythuchi.view.main.calendar.ExpenseIncome

data class TotalCategory(
    var total : Long? = null,
    var category: Category,
    var data: List<ExpenseIncome>? = null
)