package com.example.quanlythuchi.view.main.report

import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.Locale

class PercentFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return String.format(Locale.getDefault(),"%.1f%%", value)
    }
}