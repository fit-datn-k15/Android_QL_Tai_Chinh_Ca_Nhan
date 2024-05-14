package com.example.quanlythuchi

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.quanlythuchi.data.entity.Icon
import com.example.quanlythuchi.extension.formatMoney
import com.google.android.material.imageview.ShapeableImageView

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

}