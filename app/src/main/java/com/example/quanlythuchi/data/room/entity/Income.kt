package com.example.quanlythuchi.data.room.entity

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
data class Income(
    @PrimaryKey(autoGenerate = true)
    var idIncome: Int,
    var idCategory: Int,
    var date: Long,
    var income: Long,
    var note: String?
) : Serializable