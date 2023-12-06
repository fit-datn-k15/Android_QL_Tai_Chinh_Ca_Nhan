package com.example.quanlythuchi.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["idCategory"],
        childColumns = ["idCategory"]
    )]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    var idExpense: Int,
    @ColumnInfo("idCategory")
    var idCategory: Int,
    var date: Long,
    var expense: Long,
    var note: String?
) : Serializable
