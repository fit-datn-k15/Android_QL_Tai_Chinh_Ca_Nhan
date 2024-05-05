package com.example.quanlythuchi.extension

import android.util.Patterns
import com.example.quanlythuchi.base.Constance
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.formatDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern(Constance.DATE_FORMAT, Locale.getDefault())
    return this.format(formatter)
}
fun <T : Number>T.formatMoney() : String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
    return  numberFormat.format(this) + Constance.VND
}
fun String.isPasswordValid() : Boolean {
    return this.length > 6
}
fun String.isValidEmail() : Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches();
}