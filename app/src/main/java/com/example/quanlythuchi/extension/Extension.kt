package com.example.quanlythuchi.extension

import com.example.quanlythuchi.base.Constance
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatDateTime(localDate: LocalDate): String {
    val formatter = DateTimeFormatter.ofPattern(Constance.DATE_FORMAT, Locale.getDefault())
    return localDate.format(formatter)
}
