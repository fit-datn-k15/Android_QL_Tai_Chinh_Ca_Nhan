package com.example.quanlythuchi.data.entity

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
    var idIncome: Int?=null,
    var idCategory: String? =null,
    var date: String?,
    var income: Long?=null,
    var note: String?
) : Serializable
