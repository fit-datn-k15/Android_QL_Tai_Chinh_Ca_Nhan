package com.example.quanlythuchi.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable
import java.time.LocalDate

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
    var idCategory: Int?=null,
    var date: String?=null,
    var expense: Long?=null,
    var note: String?
) : Serializable
