package com.example.quanlythuchi.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
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
    var idExpense: Int?=null,
    @ColumnInfo("idCategory")
    var idCategory: String?=null,
    var date: String?=null,
    var expense: Long?=null,
    var note: String?
) : Serializable
