package com.example.quanlythuchi.view.main.home.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.repository.local.category.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class CategoryDetailViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {
    var listCategory : MutableList<Category> = mutableListOf()
    var isCategorySuccess = MutableLiveData(false)

    var nameCategory : String? = null
    fun getCategory(typeCategory: String = Fb.CategoryExpense) {
        viewModelScope.launch(Dispatchers.IO) {
            val it = categoryRepository.getAllCategory(typeCategory = typeCategory)
            withContext(Dispatchers.Main) {
                listCategory.clear()
                listCategory.addAll(it)
                isCategorySuccess.postValue(true)
            }
        }

    }
}