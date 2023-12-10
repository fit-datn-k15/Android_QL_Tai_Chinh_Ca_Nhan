package com.example.quanlythuchi.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quanlythuchi.base.Constance
import com.example.quanlythuchi.data.room.dao.CategoryDao
import com.example.quanlythuchi.data.room.dao.ExpenseDao
import com.example.quanlythuchi.data.room.entity.Category
import com.example.quanlythuchi.data.room.entity.Expense
import com.example.quanlythuchi.data.room.entity.Income
import com.example.quanlythuchi.data.room.entity.User
import com.example.quanlythuchi.data.room.entity.User_Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Category::class, Expense::class, Income::class, User_Category::class, User::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        private val name: String = Constance.NAME_DATABASE
        private var instance: AppDatabase? = null
        private var isDataBaseCreateApp = false

        @JvmStatic
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                CoroutineScope(Dispatchers.IO).launch {
                    for (dataBaseName in context.databaseList())
                        if (dataBaseName == Constance.NAME_DATABASE)
                            isDataBaseCreateApp = true
                    if (!isDataBaseCreateApp) {
                        Room.databaseBuilder(context, AppDatabase::class.java, name)
                            .build()
                            .categoryDao()
                            .insertAll(*categoryDefault().toTypedArray())
                    }
                }
                val s = Room.databaseBuilder(context, AppDatabase::class.java, name).build()
                instance = s
            }
            return instance!!

        }
    }

    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao
}

fun categoryDefault() = arrayListOf<Category>(
    Category(nameCategory = "Ăn uống", type = Constance.CATEGORY_EXPENSE),
    Category(nameCategory = "Quần áo", type = Constance.CATEGORY_EXPENSE),
    Category(nameCategory = "Mỹ Phẩm", type = Constance.CATEGORY_EXPENSE),
    Category(nameCategory = "Tiêu hàng ngày", type = Constance.CATEGORY_EXPENSE),
    Category(nameCategory = "Phí giao lưu", type = Constance.CATEGORY_EXPENSE),
    Category(nameCategory = "Y tế", type = Constance.CATEGORY_EXPENSE),
    Category(nameCategory = "Giáo dục", type = Constance.CATEGORY_EXPENSE),
    Category(nameCategory = "Tiền nhà", type = Constance.CATEGORY_EXPENSE),
    Category(nameCategory = "Tiền xe", type = Constance.CATEGORY_EXPENSE),
    Category(nameCategory = "Tiền lương", type = Constance.CATEGORY_INCOME),
    Category(nameCategory = "Tiền thưởng", type = Constance.CATEGORY_INCOME),
    Category(nameCategory = "Tiền phụ cấp", type = Constance.CATEGORY_INCOME),
    Category(nameCategory = "Tiền Đầu tư", type = Constance.CATEGORY_INCOME),
    Category(nameCategory = "Thu nhập khác", type = Constance.CATEGORY_INCOME),
)