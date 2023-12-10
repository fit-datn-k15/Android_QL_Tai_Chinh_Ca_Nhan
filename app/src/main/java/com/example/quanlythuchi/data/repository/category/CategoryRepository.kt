package com.example.quanlythuchi.data.repository.category

import com.example.quanlythuchi.data.room.entity.Category

interface CategoryRepository {
    suspend fun getCategoryExpense(type : Int) : MutableList<Category>
}