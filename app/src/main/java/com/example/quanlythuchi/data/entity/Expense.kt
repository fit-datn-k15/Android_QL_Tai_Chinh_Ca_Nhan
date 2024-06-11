package com.example.quanlythuchi.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Expense(
    var idExpense: String? = null,
    var idCategory: String? = null,
    var idUser: String? = null,
    var date: String? = null,
    var expense: Long? = null,
    var note : String? = null
): BaseDataEI(
    id = idExpense,
    idCate = idCategory,
    uuidUser = idUser,
    dateEI = date,
    money = expense,
    noteEI = note
), Parcelable
