package com.example.quanlythuchi.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    primaryKeys = ["idUser", "idCategory"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["idUser"],
            childColumns = ["idUser"]
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["idCategory"],
            childColumns = ["idCategory"]
        ),
    ]
)
data class User_Category (
    @ColumnInfo("idUser")
    var idUser: Int,
    @ColumnInfo("idCategory")
    var idCategory: Int,
)