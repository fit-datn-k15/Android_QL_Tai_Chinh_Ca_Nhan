package com.example.quanlythuchi.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable

@Entity(
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["idCategory"],
        childColumns = ["idCategory"]
    )]
)
data class Income(
    var idIncome: String? = null,
    var idCategory: String? = null,
    var idUser: String? = null,
    var date: String? = null,
    var income: Long? = null,
    var note: String? = null
) : Serializable
