package com.example.quanlythuchi.data.repository.category

import com.example.quanlythuchi.data.room.entity.Category

interface CategoryRepository {
    suspend fun getAllCategory(typeCategory : Int) : MutableList<Category>
}