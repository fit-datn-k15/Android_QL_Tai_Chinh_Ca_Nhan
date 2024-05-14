package com.example.quanlythuchi.data.repository.local.category

import com.example.quanlythuchi.data.entity.Category

interface CategoryRepository {
    suspend fun getAllCategory(typeCategory : String) : MutableList<Category>

    suspend fun addCategory(category: Category, typeCategory: String)

    suspend fun importCategoryDefault()
}