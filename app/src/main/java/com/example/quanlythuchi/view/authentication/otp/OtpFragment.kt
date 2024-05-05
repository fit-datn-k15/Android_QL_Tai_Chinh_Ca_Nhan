package com.example.quanlythuchi.view.authentication.otp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FragmentOtpBinding

class OtpFragment : BaseFragment<FragmentOtpBinding, OtpViewModel>(), OtpListener {
    override val viewModel: OtpViewModel by viewModels()
    override val layoutID: Int = R.layout.fragment_otp

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.apply {
            listener = this@OtpFragment

        }

    }

    fun openHome() {

    }

    override fun senOtp() {
        TODO("Not yet implemented")
    }

    override fun backLoginPhone() {
        findNavController().popBackStack()
    }
}

