package com.example.quanlythuchi.view.activity.home

import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.withCreated
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseActivity
import com.example.quanlythuchi.databinding.ActivityMainBinding
import com.example.quanlythuchi.databinding.NavHeaderMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityMainBinding, HomeActivityViewModel>(){
    override val viewModel: HomeActivityViewModel by viewModels()
    override val layoutId: Int = R.layout.activity_main

    private var navHostFragment : NavHostFragment?= null
    private var navController : NavController?=null
    val headerDrawer : NavHeaderMainBinding by lazy {  NavHeaderMainBinding.bind(viewBinding.navigationViewDrawer.getHeaderView(0))}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_home_container) as NavHostFragment
        navController = navHostFragment?.navController
        setUpDrawerLayout()
        navigationBottom()
            viewBinding.root.viewTreeObserver.addOnGlobalLayoutListener {
                val rect = Rect()
                viewBinding.root.getWindowVisibleDisplayFrame(rect)
                val screenHeight: Int = viewBinding.root.rootView.height
                val keypadHeight = screenHeight - rect.bottom
                if (keypadHeight > screenHeight * 0.15) {
                    this@HomeActivity.viewBinding.bottomNav.visibility = View.GONE
                } else {
                    when (navController?.currentDestination?.id) {
                        R.id.fag_home,
                        R.id.fag_calender,
                        R.id.fag_report,
                        R.id.fag_profile -> {
                            this@HomeActivity.viewBinding.bottomNav.visibility = View.VISIBLE
                        }

                        else -> {
                            this@HomeActivity.viewBinding.bottomNav.visibility = View.GONE
                        }
                    }
                }

        }
    }

    override fun onResume() {
        super.onResume()
    }
    private fun navigationBottom() {
        viewBinding.bottomNav.apply {
            navController?.let {
                setupWithNavController(it)
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
            navController?.addOnDestinationChangedListener{_,des,_ ->
                when(des.id) {
                    R.id.fag_home,
                    R.id.fag_calender,
                    R.id.fag_report,
                    R.id.fag_profile -> {
                        this@HomeActivity.viewBinding.bottomNav.visibility = View.VISIBLE
                    }
                    else -> {
                        this@HomeActivity.viewBinding.bottomNav.visibility = View.GONE
                    }
                }
            }
        }
    }
    fun setUpDrawerLayout() {
        headerDrawer.lifecycleOwner = this
        viewModel.getUserNameCurrent()
        headerDrawer.viewModel = viewModel
        viewBinding.navigationViewDrawer.setNavigationItemSelectedListener {menuItem->
            when(menuItem.itemId) {
                R.id.item_drawer_home -> {

                }
                R.id.item_drawer_calendar -> {

                }
                R.id.item_drawer_report -> {

                }
                R.id.item_drawer_excel -> {

                }
                R.id.item_drawer_pdf -> {

                }
                R.id.item_drawer_share -> {

                }
                R.id.item_drawer_infor -> {

                }
                R.id.item_drawer_buy -> {

                }
                else ->{}
            }
            true
        }
    }
}