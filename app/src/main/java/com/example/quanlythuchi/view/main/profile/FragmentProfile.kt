package com.example.quanlythuchi.view.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseFragment
import com.example.quanlythuchi.databinding.FagmentProfileBinding

class FragmentProfile : BaseFragment<FagmentProfileBinding,ProfileViewModel>(), ProfileListener {
    override val layoutID: Int = R.layout.fagment_profile
    override val viewModel: ProfileViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.lifecycleOwner = this

    }
}