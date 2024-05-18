package com.example.quanlythuchi.view.main.home.add_category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Icon
import com.example.quanlythuchi.data.repository.local.category.CategoryRepository
import com.example.quanlythuchi.view.main.home.category.FragmentCategoryDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddCategoryViewModel @Inject constructor(
 private val categoryRepository: CategoryRepository
) : BaseViewModel(){
    var idItemRcvIconSelect = -1
    var icon : String? = null
        set(value) {
            field = value
            checkValidData()
        }
    fun getListIcon() = Icon.getListIcon()
    var title : String = ""
        set(value) {
            field = value
            checkValidData()
        }
    var isEnableButtonAdd = MutableLiveData(false)

    fun checkValidData() {
        isEnableButtonAdd.value = (title.isNotEmpty() && idItemRcvIconSelect != -1)
    }
    fun addNewCategory(typeCategory : String, callback : () -> Unit) {
        if (icon == null) {return}
        val newCategory = Category (icon = icon,
            type = typeCategory,
            title = this.title
        )
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.addCategory(
                category = newCategory,
                typeCategory = typeCategory
            )
            withContext(Dispatchers.Main){
                title = ""
                callback()
            }
        }
    }
}