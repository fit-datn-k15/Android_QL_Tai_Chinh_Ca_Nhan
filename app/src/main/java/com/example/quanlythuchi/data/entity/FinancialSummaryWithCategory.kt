package com.example.quanlythuchi.data.entity

import com.example.quanlythuchi.view.main.calendar.FinancialRecord

data class FinancialSummaryWithCategory(
    var total : Long? = null,
    var category: Category,
    var listRecord: List<FinancialRecord>? = null
)