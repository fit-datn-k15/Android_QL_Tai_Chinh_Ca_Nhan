package com.example.quanlythuchi.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Patterns
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
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
fun CharSequence?.isNotNullAndNotEmpty() = !this.isNullOrEmpty()



fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInVisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun dpToPx(dp: Int, context: Context): Int =
    TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics,
    ).toInt()

internal val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

internal val Context.inputMethodManager
    get() = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

internal fun Context.getDrawableCompat(@DrawableRes drawable: Int): Drawable =
    requireNotNull(ContextCompat.getDrawable(this, drawable))

internal fun Context.getColorCompat(@ColorRes color: Int) =
    ContextCompat.getColor(this, color)

internal fun TextView.setTextColorRes(@ColorRes color: Int) =
    setTextColor(context.getColorCompat(color))

//fun Fragment.addStatusBarColorUpdate(@ColorRes colorRes: Int) {
//    view?.findViewTreeLifecycleOwner()?.lifecycle?.addObserver(
//        StatusBarColorLifecycleObserver(
//            requireActivity(),
//            requireContext().getColorCompat(colorRes),
//        ),
//    )
//}