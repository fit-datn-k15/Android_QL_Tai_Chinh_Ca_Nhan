package com.example.quanlythuchi.data.repository.category

import com.example.quanlythuchi.base.Constant
import com.example.quanlythuchi.data.room.dao.CategoryDao
import com.example.quanlythuchi.data.room.entity.Category
import javax.inject.Inject

class CategoryRepositoryImp @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {
    override suspend fun getAllCategory(typeCategory: Int): MutableList<Category> {
        val categoryList = categoryDao.getCategory(Constant.CATEGORY_EXPENSE)
        categoryList.add(Category(nameCategory = "+", idCategory = -1, type = typeCategory))
        return categoryList
    }
}