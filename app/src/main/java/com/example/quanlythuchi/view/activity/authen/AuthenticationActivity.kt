package com.example.quanlythuchi.view.activity.authen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseActivity
import com.example.quanlythuchi.databinding.ActivityAuthenticationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : BaseActivity<ActivityAuthenticationBinding, AuthenticationViewModel>() {
    override val viewModel: AuthenticationViewModel by viewModels()
    override val layoutId: Int = R.layout.activity_authentication
    var navHostFragment : NavHostFragment?= null
    var navController : NavController?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        navHostFragment = supportFragmentManager.findFragmentById(viewBinding.navContainer.id) as NavHostFragment
        navController = navHostFragment?.navController
    }

}