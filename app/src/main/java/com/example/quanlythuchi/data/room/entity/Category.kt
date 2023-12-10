package com.example.quanlythuchi.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.w3c.dom.Text

@Entity(tableName = "Category")
data class Category (
    @PrimaryKey(autoGenerate = true)
    var idCategory: Int? = null,
    var nameCategory: String,
    var type : Int
)