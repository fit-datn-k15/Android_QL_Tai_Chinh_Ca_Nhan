package com.example.quanlythuchi

import android.widget.ImageButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.quanlythuchi.data.entity.Icon
import com.example.quanlythuchi.extension.formatDateTime
import com.example.quanlythuchi.extension.formatMoney
import com.google.android.material.imageview.ShapeableImageView
import java.time.LocalDate

object AppBindingAdapter {
    @JvmStatic
    @BindingAdapter("setTextFormat")
    fun <T:Number> TextView.setTextFormat(money : T) {
        text = money.formatMoney()
    }
    @JvmStatic
    @BindingAdapter("setIcon")
    fun ShapeableImageView.setIcon(name : String?) {
        if (name == null) {
           return
        }
        this.setImageResource(Icon.getIcon(name))
    }
    @JvmStatic
    @BindingAdapter("setIconEnableInputData")
    fun ImageButton.setIconEnableInputData(flag : Boolean) {
        this.isEnabled = flag
        if (flag) {
            this.setImageResource(R.drawable.ic_input_data_enable)
        }
        else {
            this.setImageResource(R.drawable.ic_input_data_disable)
        }
    }
    @JvmStatic
    @BindingAdapter("setTimeFormatter")
    fun TextView.setTimeFormatter(time : LocalDate) {
        text = time.formatDateTime()
    }

}