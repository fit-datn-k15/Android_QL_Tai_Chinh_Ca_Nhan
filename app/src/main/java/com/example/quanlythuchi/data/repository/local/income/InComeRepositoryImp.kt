package com.example.quanlythuchi.data.repository.local.income

import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.data.entity.Expense
import com.example.quanlythuchi.data.mapperExpense
import com.example.quanlythuchi.data.entity.Income
import com.example.quanlythuchi.data.mapperIncome
import com.example.quanlythuchi.extension.formatMonth
import com.example.quanlythuchi.extension.toLocalDate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class InComeRepositoryImp @Inject constructor(

) : InComeRepository{
    private val db: FirebaseFirestore = Firebase.firestore
    private val user by lazy {  FirebaseAuth.getInstance().currentUser}
    override suspend fun getAllIncome():MutableList<Income>{
        if(user == null) {return mutableListOf() }
        val listIncome = mutableListOf<Income>()
        db.collection(Fb.Income)
            .whereEqualTo(Fb.CategoryField.idUser, user!!.uid)
            .get()
            .addOnSuccessListener {querySnapshot ->
                querySnapshot.documents.forEach {document ->
                    document.toObject(Income::class.java)?.let {
                        listIncome.add(it)
                    }
                }
            }
            .addOnFailureListener {}
            .await()
        return listIncome
    }

    override suspend fun insertIncome(income: Income): Boolean {
        if(user == null) {return false }
        var result = false
        income.idUser = user!!.uid
        db.collection(Fb.Income)
            .add(income)
            .addOnCompleteListener{
                result = true
            }
            .addOnFailureListener {
                result = false
            }
            .await()
        return result
    }

    override suspend fun getIncomeByDate(date: String): List<Income> {
        return listOf()
    }

    override suspend fun getIncomeByMonth(month: String): List<Income> {
        if (user == null) {
            return mutableListOf()
        }
        val listIncome = mutableListOf<Income>()
        db.collection(Fb.Income)
            .whereEqualTo(Fb.CategoryField.idUser, user!!.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (doc in querySnapshot.documents) {
                    val item = doc.toObject(Income::class.java)
                    if (item != null && item.date?.toLocalDate()?.formatMonth() == month) {
                        listIncome.add(item)
                    }
                }
            }
            .addOnFailureListener {}
            .await()
        return listIncome
    }

    override suspend fun deleteIncome(income: Income): Boolean {
        if (user == null) { return false}
        var result = false
        income.idIncome?.let {
            db.collection(Fb.Income)
                .document(it)
                .delete()
                .addOnCompleteListener{
                    result = true
                }.addOnFailureListener {result = false}
                .await()
        }
        return result

    }

    override suspend fun updateIncome(income: Income): Boolean {
        if (user == null) { return false}
        var result = false
        income.idIncome?.let {
            db.collection(Fb.Income)
                .document(it)
                .update(mapOf(
                    "idIncome" to income.idIncome,
                    "idUser" to income.idUser,
                    "idCategory" to income.idCategory,
                    "note" to income.note,
                    "date" to income.date,
                    "income" to income.income
                ))
                .addOnCompleteListener {
                    result = true
                }
                .addOnFailureListener {
                    result = false
                }
                .await()
        }
        return result
    }
}