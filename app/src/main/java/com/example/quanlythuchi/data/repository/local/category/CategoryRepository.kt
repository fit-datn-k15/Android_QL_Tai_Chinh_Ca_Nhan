package com.example.quanlythuchi.data.repository.local.category

import com.example.quanlythuchi.data.entity.Category

interface CategoryRepository {
    suspend fun getAllCategoryByType(typeCategory : String) : MutableList<Category>
    suspend fun getAll() : MutableList<Category>

    suspend fun addCategory(category: Category, typeCategory: String)

    suspend fun importCategoryDefault()

    suspend fun removeCategory(category: Category,typeCategory: String) : Boolean
}