package com.example.quanlythuchi.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Income(
    var idIncome: String? = null,
    var idCategory: String? = null,
    var idUser: String? = null,
    var date: String? = null,
    var income: Long? = null,
    var note: String? = null
) : BaseDataEI(
    id = idIncome,
    idCate = idCategory,
    uuidUser = idUser,
    dateEI = date,
    money = income,
    noteEI = note
), Parcelable
