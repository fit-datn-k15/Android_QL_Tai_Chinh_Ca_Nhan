package com.example.quanlythuchi.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<ViewBinding : ViewDataBinding, viewModel : BaseViewModel> : Fragment() {

    protected lateinit var viewBinding : ViewBinding
    protected abstract val viewModel: viewModel
    protected abstract val layoutID : Int
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater,layoutID,container,false)
        viewBinding.lifecycleOwner = this
        return viewBinding.root
    }


}