package com.example.quanlythuchi.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
data class Expense(
    var idExpense: String? = null,
    var idCategory: String? = null,
    var idUser: String? = null,
    var date: String? = null,
    var expense: Long? = null,
    var note: String? = null
) : Parcelable
