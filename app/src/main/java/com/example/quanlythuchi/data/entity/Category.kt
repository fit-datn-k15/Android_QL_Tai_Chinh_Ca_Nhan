package com.example.quanlythuchi.data.entity

import com.google.firebase.firestore.DocumentSnapshot


data class Category (
    var idCategory: String? = null,
    var icon: String? = null,
    var title: String? = null,
    var type : String? = null
) {
    companion object {
        fun categoryAdded() = Category(icon = Icon.ic_30, title = "Thêm")
    }
}
fun DocumentSnapshot.MapperCategory(typeCategory: String) : Category{
    return Category(
        idCategory = this.id,
        icon = this["icon"] as? String?,
        title = this["title"] as? String,
        type = this["type"] as? String ?: typeCategory
    )
}

