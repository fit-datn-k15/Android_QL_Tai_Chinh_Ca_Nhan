package com.example.quanlythuchi.view.activity.home

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseActivity
import com.example.quanlythuchi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding, HomeActivityViewModel>() {
    override val viewModel: HomeActivityViewModel by viewModels()
    override val layoutId: Int = R.layout.activity_main

    private var navHostFragment : NavHostFragment?= null
    private var navController : NavController?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        navController = navHostFragment?.navController
        navigationBottom()

    }
    private fun navigationBottom() {
        viewBinding.bottomNav.apply {
            navController?.let {
                setupWithNavController(it)
            }
            navController?.addOnDestinationChangedListener{_,des,_ ->
                when(des.id) {
                    R.id.fag_home,
                    R.id.fag_calender,
                    R.id.fag_report,
                    R.id.fag_profile -> {
                        viewBinding.bottomNav.visibility = View.VISIBLE
                    }
                    else -> {
                        viewBinding.bottomNav.visibility = View.GONE
                    }
                }
            }
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.fag_home -> {
                        navController?.navigate(R.id.fag_home)
                        true
                    }

                    R.id.fag_calender -> {
                        navController?.navigate(R.id.fag_calender)
                        true
                    }
                    R.id.fag_report -> {
                        navController?.navigate(R.id.fag_report)
                        true
                    }
                    else -> {
                        viewBinding.drawer.openDrawer(GravityCompat.END)
                        false
                    }
                }
            }
        }
    }
}