package com.qltc.finace.view.authentication.sign_up

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.qltc.finace.R
import com.qltc.finace.base.BaseFragment
import com.qltc.finace.databinding.FragmentSignUpBinding
import com.qltc.finace.view.activity.authen.AuthenticationActivity
import com.qltc.finace.view.activity.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel>(), SignUpListener {
    override val viewModel: SignUpViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_sign_up
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@SignUpFragment
            viewModel = this@SignUpFragment.viewModel
        }
    }

    override fun signUp() {
        viewModel.signUp(callback = { success, message ->
            if (success) {
                viewModel.insertDefaultCategory {
                    Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                    navigateToHome()
                }
            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToHome() {
        val intent = Intent(getOwnerActivity<AuthenticationActivity>(), HomeActivity::class.java)
        getOwnerActivity<AuthenticationActivity>()?.startActivity(intent)
        getOwnerActivity<AuthenticationActivity>()?.finish()
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