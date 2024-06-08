package com.example.quanlythuchi.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.quanlythuchi.view.main.calendar.ExpenseIncome
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Income(
    var idIncome: String? = null,
    var idCategory: String? = null,
    var idUser: String? = null,
    var date: String? = null,
    var income: Long? = null,
    var note: String? = null
) : Serializable, Parcelable
