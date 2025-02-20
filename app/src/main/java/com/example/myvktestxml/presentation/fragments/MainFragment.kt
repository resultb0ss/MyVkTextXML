package com.example.myvktestxml.presentation.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.myvktestxml.databinding.FragmentMainBinding
import com.example.myvktestxml.presentation.viewmodels.MainViewModel

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel = ViewModelProvider(this)[MainViewModel::class]

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

}