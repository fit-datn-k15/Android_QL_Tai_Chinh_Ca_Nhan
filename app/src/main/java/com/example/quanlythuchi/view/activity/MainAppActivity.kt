package com.example.quanlythuchi.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseActivity
import com.example.quanlythuchi.databinding.ActivityMainBinding

class MainAppActivity : BaseActivity<ActivityMainBinding, MainAppViewModel>() {
    override val viewModel: MainAppViewModel by viewModels()
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
        }
    }
}