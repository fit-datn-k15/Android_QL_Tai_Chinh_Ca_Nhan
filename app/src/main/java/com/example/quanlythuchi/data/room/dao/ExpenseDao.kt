package com.example.quanlythuchi.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.quanlythuchi.data.room.entity.Expense

@Dao
interface ExpenseDao {
    @Insert(entity = Expense::class, onConflict = OnConflictStrategy.ABORT)
    fun insertExpense(expense: Expense) : Long
    @Query("Select * From Expense")
    fun getAllExpense() : List<Expense>
    @Query ("Select * From Expense WHERE date = :date")
    fun getExpenseByDate(date: String) : List<Expense>

}