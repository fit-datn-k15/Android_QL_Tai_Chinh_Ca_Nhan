package com.example.quanlythuchi.view.fragment.income

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.Constance
import com.example.quanlythuchi.data.room.AppDatabase
import com.example.quanlythuchi.data.room.entity.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IncomeViewModel : BaseViewModel() {
    val categorys = MutableLiveData<MutableList<Category>>()
    fun getCategory(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context).getCategoryDao()
            val it = db.getCategory(Constance.CATEGORY_INCOME)
            withContext(Dispatchers.Main) {
                categorys.postValue(it)
            }
        }
    }

}