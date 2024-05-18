package com.example.quanlythuchi.extension

import android.util.Patterns
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quanlythuchi.base.Constant
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDate.formatDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT, Locale.getDefault())
    return this.format(formatter)
}
fun <T : Number>T.formatMoney() : String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
    return  numberFormat.format(this) + Constant.VND
}
fun String.isPasswordValid() : Boolean {
    return this.length > 6
}
fun String.isValidEmail() : Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches();
}
fun <T, VH : RecyclerView.ViewHolder> ListAdapter<T, VH>.updateList(list: List<T>?) {

    this.submitList(if (list === this.currentList) list.toMutableList() else list)
}