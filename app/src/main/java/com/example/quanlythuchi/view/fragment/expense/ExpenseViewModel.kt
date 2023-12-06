package com.example.quanlythuchi.view.fragment.expense

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.base.Constance
import com.example.quanlythuchi.data.room.AppDatabase
import com.example.quanlythuchi.data.room.entity.Category
import com.example.quanlythuchi.view.adapter.AdapterExpense
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.Calendar

class ExpenseViewModel : BaseViewModel() {
    var categorys = MutableLiveData<MutableList<Category>>()
    var day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var month = Calendar.getInstance().get(Calendar.MONTH)
    var year = Calendar.getInstance().get(Calendar.YEAR)
    fun getCategory(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val db = AppDatabase.getInstance(context).getCategoryDao()
            val it = db.getCategory(Constance.CATEGORY_EXPENSE)
            withContext(Dispatchers.Main) {
                categorys.postValue(it)
            }
        }
    }
}