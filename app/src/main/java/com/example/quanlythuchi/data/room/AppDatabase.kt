package com.example.quanlythuchi.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.Constance
import com.example.quanlythuchi.data.room.dao.CategoryDao
import com.example.quanlythuchi.data.room.dao.ExpenseDao
import com.example.quanlythuchi.data.room.entity.Category
import com.example.quanlythuchi.data.room.entity.Expense
import com.example.quanlythuchi.data.room.entity.Income
import com.example.quanlythuchi.data.room.entity.User
import com.example.quanlythuchi.data.room.entity.User_Category

@Database(
    entities = [Category::class, Expense::class, Income::class, User_Category::class, User::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private val name: String = Constance.NAME_DATABASE
        private var Instance: AppDatabase? = null
        @JvmStatic
        fun getInstance(context: Context, ): AppDatabase {
            if (Instance == null) {
                val s = Room.databaseBuilder(context,AppDatabase::class.java,name).createFromAsset("QLTC.db").build()
                Instance = s
            }
            return Instance!!
        }
    }
    abstract fun setExpenseDao() : ExpenseDao
    abstract fun getCategoryDao(): CategoryDao

}