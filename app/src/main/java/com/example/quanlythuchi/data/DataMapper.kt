package com.example.quanlythuchi.data

import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Expense
import com.google.firebase.firestore.DocumentSnapshot

fun DocumentSnapshot.MapperCategory(typeCategory: String) : Category {
    return Category(
        idCategory = this.id,
        icon = this["icon"] as? String?,
        title = this["title"] as? String,
        type = this["type"] as? String ?: typeCategory
    )
}
fun DocumentSnapshot.MapperExpense() : Expense {
    return Expense(
        idExpense = this.id,
        idCategory = this["idCategory"] as? String?,
        idUser = this["idUser"] as? String?,
        expense = this["expense"] as? Long?,
        note = this["note"] as? String?
    )
}