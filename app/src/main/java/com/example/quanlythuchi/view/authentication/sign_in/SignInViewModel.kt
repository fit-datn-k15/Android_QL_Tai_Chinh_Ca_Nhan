package com.example.quanlythuchi.view.authentication.sign_in

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.data.repository.local.category.CategoryRepository
import com.google.android.material.snackbar.ContentViewCallback
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.internal.operators.maybe.MaybeDoAfterSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val categoryRepository: CategoryRepository
): BaseViewModel() {

    fun insertDefaultCategory(isNewsUser : Boolean,success : () -> Unit) {
        if (!isNewsUser) {
            success()
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository.importCategoryDefault()
            withContext(Dispatchers.Main) {
                success()
            }

        }
    }

}