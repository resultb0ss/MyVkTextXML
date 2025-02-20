package com.example.myvktestxml.presentation.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myvktestxml.databinding.FragmentMainBinding
import com.example.myvktestxml.domain.models.Resource
import com.example.myvktestxml.presentation.adapters.MainAdapter
import com.example.myvktestxml.presentation.viewmodels.MainViewModel
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>() {

    private val viewModel = ViewModelProvider(this)[MainViewModel::class]
    private val videoAdapter = MainAdapter { data ->
        val action =
            MainFragmentDirections.Companion
                .actionMainFragmentToVideoPlayerFragment(data.mp4Url)
        findNavController().navigate(action)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMainBinding {
        return FragmentMainBinding.inflate(inflater, container, false)
    }

    private fun initLayout() {
        binding.mainFragmentRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = videoAdapter
            setHasFixedSize(true)
        }

        binding.apply {
            mainFragmentSwipeRefreshLayout.setOnRefreshListener {
                viewModel.loadVideos()
                mainFragmentSwipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initLayout()
        observeVideos()
    }

    private fun observeVideos() {
        lifecycleScope.launch {
            viewModel.videos.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.mainFragmentProgressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.mainFragmentProgressBar.visibility = View.GONE
                        videoAdapter.submitList(resource.data!!)
                    }

                    is Resource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            resource.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}