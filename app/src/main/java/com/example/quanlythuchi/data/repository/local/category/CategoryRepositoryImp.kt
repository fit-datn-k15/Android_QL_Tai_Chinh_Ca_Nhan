package com.example.quanlythuchi.data.repository.local.category

import android.util.Log
import com.example.quanlythuchi.base.TAG
import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.data.MapperCategory
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.categoryExpense
import com.example.quanlythuchi.data.entity.categoryIncome
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.log

class CategoryRepositoryImp @Inject constructor(

) : CategoryRepository {

    private val db: FirebaseFirestore = Firebase.firestore
    private val user by lazy {  FirebaseAuth.getInstance().currentUser}
    override suspend fun getAllCategory(typeCategory: String): MutableList<Category> {
        if (user == null) {return  mutableListOf() }

        val listCate = mutableListOf<Category>()
        db.collection(Fb.User)
            .document(user!!.uid)
            .collection(typeCategory)
            .get()
            .addOnSuccessListener { querySnapShot ->
                querySnapShot?.documents?.forEach {
                    listCate.add(it.MapperCategory(typeCategory))
                }
            }
            .addOnFailureListener { ex -> Log.e(TAG, "getAllCategory: $ex") }
            .await()
        return listCate
    }

    override suspend fun addCategory(category: Category, typeCategory: String) {
        if (user == null) return
        db.collection(Fb.User)
            .document(user!!.uid)
            .collection(typeCategory)
            .add(category)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
            }.addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }.await()
    }

    override suspend fun importCategoryDefault() {
        if (user == null) {return }
        val batch = db.batch()
        categoryExpense().forEach{
            val docRef = db.collection(Fb.User)
                            .document(user!!.uid)
                            .collection(Fb.CategoryExpense)
                            .document()
            batch.set(docRef, it);
        }
        categoryIncome().forEach{
            val docRef = db.collection(Fb.User)
                .document(user!!.uid)
                .collection(Fb.CategoryIncome)
                .document()
            batch.set(docRef, it);
        }
        batch.commit().addOnCompleteListener{
            Log.d(TAG, "addOnCompleteListener: ${it.result}")
        }.addOnFailureListener {
            Log.w(TAG, "addOnFailureListener : $it")
        }.await()
    }

    override suspend fun removeCategory(category: Category, typeCategory: String): Boolean {
        if (user == null) return false
        var deferredResult = false
        category.idCategory?.let {
            db.collection(Fb.User).document(user!!.uid)
                .collection(typeCategory)
                .document(it)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "removeCategory: Success ${category.idCategory}")
                    deferredResult = true
                }
                .addOnFailureListener {
                    Log.d(TAG, "removeCategory: Fail ${category.idCategory}")
                    deferredResult = false
                }.await()
        }
        return deferredResult
    }

}
