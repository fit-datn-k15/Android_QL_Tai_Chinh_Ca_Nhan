package com.example.quanlythuchi

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.quanlythuchi.extension.formatMoney

object AppBindingAdapter {
    @JvmStatic
    @BindingAdapter("setTextFormat")
    fun <T:Number> TextView.setTextFormat(money : T) {
        text = money.formatMoney()
    }
}