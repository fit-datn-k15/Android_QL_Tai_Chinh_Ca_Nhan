package com.example.quanlythuchi.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    var navHostFragment : NavHostFragment?= null
    var navController : NavController?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_container) as NavHostFragment
        navController = navHostFragment?.navController
        navigationBottom()

    }
    fun navigationBottom() {
        binding.bottomNav.apply {
            navController?.let {
                setupWithNavController(it)
            }
            navController?.addOnDestinationChangedListener{_,des,_ ->
                when(des.id) {
                    R.id.fag_home,
                    R.id.fag_calender,
                    R.id.fag_report,
                    R.id.fag_profile -> {
                        binding.bottomNav.visibility = View.VISIBLE
                    }
                    else -> {
                        binding.bottomNav.visibility = View.GONE
                    }
                }
            }
        }
    }
}