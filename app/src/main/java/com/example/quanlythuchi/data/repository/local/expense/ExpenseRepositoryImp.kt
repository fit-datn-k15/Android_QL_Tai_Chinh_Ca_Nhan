package com.example.quanlythuchi.data.repository.local.expense

import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.data.mapperExpense
import com.example.quanlythuchi.data.entity.Expense
import com.example.quanlythuchi.extension.formatMonth
import com.example.quanlythuchi.extension.toLocalDate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExpenseRepositoryImp @Inject constructor(

): ExpenseRepository {
    private val db: FirebaseFirestore = Firebase.firestore
    private val user by lazy {  FirebaseAuth.getInstance().currentUser}
    override suspend fun getAllExpense(): MutableList<Expense> {
        if(user == null) {return mutableListOf() }
        val listExpense = mutableListOf<Expense>()
        db.collection(Fb.Expense)
            .whereEqualTo(Fb.CategoryField.idUser, user!!.uid)
            .get()
            .addOnSuccessListener {querySnapshot ->
                querySnapshot.documents.forEach {document ->
                    document.toObject(Expense::class.java)?.let {
                            expense -> listExpense.add(expense)
                    }
                }
            }
            .addOnFailureListener {

            }
            .await()
        return listExpense
    }

    override suspend fun insertExpense(expense: Expense): Boolean {
        if(user == null) {return false }
        var result = false
        expense.idUser = user!!.uid
        db.collection(Fb.Expense)
            .add(expense)
            .addOnCompleteListener{
                result = true
            }
            .addOnFailureListener {
                result = false
            }
            .await()
        return result
    }

    override suspend fun getExpenseByDay(date: String): List<Expense> {
        if (user == null) {
            return mutableListOf()
        }
        val listExpense = mutableListOf<Expense>()
        db.collection(Fb.Expense)
            .whereEqualTo(Fb.CategoryField.idUser, user!!.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (item in querySnapshot.documents) {
                    if (item["date"] == date) {
                        listExpense.add(item.mapperExpense())
                    }
                }
            }
            .addOnFailureListener {}
            .await()
        return listOf()
    }

    override suspend fun getExpenseByWeek(week: String): List<Expense> {
//        if (user == null ) {return listOf() }
//        val listExpense = mutableListOf<Expense>()
//        db.collection(Fb.Expense)
//            .whereEqualTo(Fb.CategoryField.idUser, user!!.uid)
//            .get()
//            .addOnSuccessListener { querySnapshot ->
//                for (item in querySnapshot.documents) {
//                    if (item["week"] == week) {
//                        listExpense.add(item.mapperExpense())
//                    }
//                }
//            }
//            .addOnFailureListener {}
//            .await()
//
//        return  listExpense
        return listOf()
    }

    override suspend fun getExpenseByMonth(month: String): List<Expense> {
        if (user == null) {
            return mutableListOf()
        }
        val listExpense = mutableListOf<Expense>()
        db.collection(Fb.Expense)
            .whereEqualTo(Fb.CategoryField.idUser, user!!.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (doc in querySnapshot.documents) {
                    val item = doc.toObject(Expense::class.java)
                    if (item != null && item.date?.toLocalDate()?.formatMonth() == month) {
                        listExpense.add(item)
                    }
                }
            }
            .addOnFailureListener {}
            .await()
        return listExpense
    }
}