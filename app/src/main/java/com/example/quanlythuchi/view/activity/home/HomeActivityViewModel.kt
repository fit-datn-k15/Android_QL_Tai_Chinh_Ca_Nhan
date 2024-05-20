package com.example.quanlythuchi.view.activity.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(
    @ApplicationContext val applicationContext: Context
) : BaseViewModel() {

    var userName = MutableLiveData<String>("OKE")

    fun getUserNameCurrent() {
        userName.postValue(FirebaseAuth.getInstance().currentUser?.displayName  ?: applicationContext.getString(
            R.string.when_not_name_user))
    }
}