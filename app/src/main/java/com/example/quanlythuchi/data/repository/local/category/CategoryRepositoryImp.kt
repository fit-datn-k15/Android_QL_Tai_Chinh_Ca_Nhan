package com.example.quanlythuchi.data.repository.local.category

import android.util.Log
import com.example.quanlythuchi.base.TAG
import com.example.quanlythuchi.data.Fb
import com.example.quanlythuchi.data.entity.Category
import com.example.quanlythuchi.data.entity.Icon
import com.example.quanlythuchi.data.entity.MapperCategory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class CategoryRepositoryImp @Inject constructor(

) : CategoryRepository {

    private val db: FirebaseFirestore = Firebase.firestore
    private val user by lazy {  FirebaseAuth.getInstance().currentUser}
    override suspend fun getAllCategory(typeCategory: String): MutableList<Category> {
        if (user == null) {return  mutableListOf() }
        val categoryList = db.collection(Fb.User)
            .document(user!!.uid)
            .collection(typeCategory)
        val listCate = mutableListOf<Category>()
        categoryList.get()
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
        db.collection(Fb.User).document(user!!.uid).collection(typeCategory).add(category).addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot written with ID: ${documentReference.id}")
        }.addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }
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

}


fun categoryExpense() = arrayListOf(
    Category(title = "Ăn uống", type = Fb.CategoryExpense, icon = Icon.ic_1),
    Category(title = "Quần áo", type = Fb.CategoryExpense, icon = Icon.ic_2),
    Category(title = "Mỹ Phẩm", type = Fb.CategoryExpense, icon = Icon.ic_3),
    Category(title = "Tiêu hàng ngày", type = Fb.CategoryExpense, icon = Icon.ic_4),
    Category(title = "Phí giao lưu", type = Fb.CategoryExpense, icon = Icon.ic_6),
    Category(title = "Y tế", type = Fb.CategoryExpense, icon = Icon.ic_5),
    Category(title = "Giáo dục", type = Fb.CategoryExpense, icon = Icon.ic_12),
    Category(title = "Tiền nhà", type = Fb.CategoryExpense, icon = Icon.ic_9),
    Category(title = "Tiền xe", type = Fb.CategoryExpense, icon = Icon.ic_8),
)
fun categoryIncome() = arrayListOf(
    Category(title = "Tiền lương", type = Fb.CategoryIncome, icon = Icon.ic_12),
    Category(title = "Tiền thưởng", type = Fb.CategoryIncome, icon = Icon.ic_22),
    Category(title = "Tiền phụ cấp", type = Fb.CategoryIncome, icon = Icon.ic_23),
    Category(title = "Tiền Đầu tư", type = Fb.CategoryIncome, icon = Icon.ic_16),
    Category(title = "Thu nhập khác", type = Fb.CategoryIncome, icon = Icon.ic_25),
)