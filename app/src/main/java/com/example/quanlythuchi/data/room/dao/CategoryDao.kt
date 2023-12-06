package com.example.quanlythuchi.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.quanlythuchi.data.room.entity.Category

@Dao
interface CategoryDao {
   @Query("SELECT * FROM Category Where type = :type")
    fun getCategory(type:Int): MutableList<Category>
}