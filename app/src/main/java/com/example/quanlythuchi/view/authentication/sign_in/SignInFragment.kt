package com.example.quanlythuchi.view.authentication.sign_in

import android.content.Intent
import android.database.Observable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.base.KeyboardManager
import com.example.quanlythuchi.databinding.FragmentSignInBinding
import com.example.quanlythuchi.view.activity.AuthenticationActivity
import com.example.quanlythuchi.view.activity.HomeActivity

class SignInFragment : BaseFragment<FragmentSignInBinding,SignInViewModel>(),SignInListener, Observer<KeyboardManager.KeyboardStatus>{
    override val viewModel: SignInViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_sign_in
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@SignInFragment
            viewModel = this@SignInFragment.viewModel
        }
        this.getOwnerActivity<AuthenticationActivity>()
            ?.let { KeyboardManager.init(it).status()?.observeForever(this) }
    }
    override fun openLoginPhone() {
        Toast.makeText(requireContext(), "Chức năng đang phát triển", Toast.LENGTH_SHORT).show()
    }

    override fun openSignInGoogle() {
        Toast.makeText(requireContext(), "Chức năng đang phát triển", Toast.LENGTH_SHORT).show()

    }

    override fun openSignInFacebook() {
        Toast.makeText(requireContext(), "Chức năng đang phát triển", Toast.LENGTH_SHORT).show()
    }

    override fun openApp() {
        val intent = Intent(getOwnerActivity<AuthenticationActivity>(), HomeActivity::class.java)
        getOwnerActivity<AuthenticationActivity>()?.startActivity(intent)
    }

    override fun openSignUp() {
        findNavController().navigate(R.id.fag_sign_up)
    }

    override fun onChanged(value: KeyboardManager.KeyboardStatus) {
        if (value == KeyboardManager.KeyboardStatus.OPEN) {
            val layoutParamsLogo = viewBinding.logo.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsLogo.verticalBias = 0.1f
            viewBinding.logo.layoutParams = layoutParamsLogo

            val layoutParamsLoginWithEmail = viewBinding.loginWithEmail.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsLoginWithEmail.verticalBias = 0.1f
            viewBinding.loginWithEmail.layoutParams = layoutParamsLoginWithEmail
        }
        else {
            val layoutParamsLogo = viewBinding.logo.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsLogo.verticalBias = 0.17f
            viewBinding.logo.layoutParams = layoutParamsLogo

            val layoutParamsLoginWithEmail = viewBinding.loginWithEmail.layoutParams as ConstraintLayout.LayoutParams
            layoutParamsLoginWithEmail.verticalBias = 0.2f
            viewBinding.loginWithEmail.layoutParams = layoutParamsLoginWithEmail
        }
    }
}