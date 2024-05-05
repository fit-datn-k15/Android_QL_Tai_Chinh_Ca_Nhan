package com.example.quanlythuchi.view.authentication.sign_in

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FragmentSignInBinding
import com.example.quanlythuchi.view.activity.AuthenticationActivity
import com.example.quanlythuchi.view.activity.HomeActivity

class SignInFragment : BaseFragment<FragmentSignInBinding,SignInViewModel>(),SignInListener{
    override val viewModel: SignInViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_sign_in
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@SignInFragment
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
}