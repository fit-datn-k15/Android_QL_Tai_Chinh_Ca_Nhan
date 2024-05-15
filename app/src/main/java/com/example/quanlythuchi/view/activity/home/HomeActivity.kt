package com.example.quanlythuchi.view.activity.home

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseActivity
import com.example.quanlythuchi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding, HomeActivityViewModel>(){
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
        viewBinding.root.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            viewBinding.root.getWindowVisibleDisplayFrame(rect)
            val screenHeight: Int = viewBinding.root.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            if (keypadHeight > screenHeight * 0.15) {
                this@HomeActivity.viewBinding.bottomNav.visibility = View.GONE
            } else {
                this@HomeActivity.viewBinding.bottomNav.visibility = View.VISIBLE
            }
        }
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