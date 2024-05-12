package com.example.quanlythuchi.view.authentication.sign_up

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FragmentSignUpBinding
import com.example.quanlythuchi.view.activity.authen.AuthenticationActivity
import com.example.quanlythuchi.view.activity.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>(), SignUpListener {
    override val viewModel: SignUpViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_sign_up
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@SignUpFragment
            viewModel = this@SignUpFragment.viewModel
        }
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

    override fun backSignUp() {
        findNavController().popBackStack()
    }
}