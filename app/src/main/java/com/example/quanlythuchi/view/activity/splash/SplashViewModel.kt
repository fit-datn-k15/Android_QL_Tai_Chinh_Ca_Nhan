package com.example.quanlythuchi.view.activity.splash

import com.example.quanlythuchi.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject  constructor(

) : BaseViewModel() {
    var firebaseAuth = FirebaseAuth.getInstance();
    // Initialize firebase user
    var firebaseUser : FirebaseUser? = null

    fun checkUser () : Boolean {
        firebaseUser = firebaseAuth.currentUser
        return firebaseUser != null
    }

}