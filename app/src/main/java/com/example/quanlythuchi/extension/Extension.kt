package com.example.quanlythuchi.extension

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Patterns
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.Constant
import com.example.quanlythuchi.data.entity.Category
import java.text.NumberFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale


fun NavController.navigateWithAnim(@IdRes resId: Int, args: Bundle?) {
    this.navigate(resId, args)
}
fun YearMonth.formatMonthVN() : String{
    val formatter = DateTimeFormatter.ofPattern("MM/yyyy", Locale.getDefault())
    return this.format(formatter)
}

fun LocalDate.formatDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT, Locale.getDefault())
    return this.format(formatter)
}
fun LocalDate.toMonthYearString(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM",Locale.getDefault())

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
fun CharSequence?.isNotNullAndNotEmpty() = !this.isNullOrEmpty()

//fun String.toLocalDate() : LocalDate {
//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//    return LocalDate.parse(this, formatter)
//}
fun String?.toLocalDate() : LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    try {
        if (this.isNullOrEmpty()) {
            return LocalDate.parse(LocalDate.now().toString(), formatter)
        }
        return LocalDate.parse(this, formatter)
    }
    catch (e : Exception) {
        return LocalDate.parse(LocalDate.now().toString(), formatter)
    }
}

internal fun Context.getColorCompat(@ColorRes color: Int) =
    ContextCompat.getColor(this, color)

internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(context.getColorCompat(color))


// kiểm tra xem ngườid dùng có chọn ngày nào ko. nếu ko chọn ngày nào thì hiển thị tên tháng đó
fun TextView.setTimeSelected(time : LocalDate?, yearMonth: YearMonth?, isSelectedDay : Boolean) {
    when(true) {
        (isSelectedDay && time != null) -> {
            text = time.formatDateTime()
        }
        (yearMonth != null ) -> {
            text = context.getString(R.string.selected_date_to_show_information, yearMonth.formatMonthVN())
        }
        else -> {
            return
        }
    }
}
fun <T: Parcelable> MutableList<T>.toParcelableArrayList(): ArrayList<out Parcelable> {
    val data = ArrayList<Parcelable>()
    this.forEach { data.add(it) }
    return data
}
