package com.example.quanlythuchi.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quanlythuchi.data.room.entity.Expense
import com.example.quanlythuchi.data.room.entity.Income

@Dao
interface IncomeDao {
    @Insert(entity = Income::class, onConflict = OnConflictStrategy.ABORT)
    fun insertIncome(income: Income) : Long
    @Query("SELECT * From Income")
    fun getAllIncome() : List<Income>

    @Query("SELECT * From Income WHERE date =:date")
    fun getAllIncomeByDate(date : String) : List<Income>
}