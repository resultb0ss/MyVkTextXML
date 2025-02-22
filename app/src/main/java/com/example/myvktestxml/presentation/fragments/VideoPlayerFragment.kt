package com.example.myvktestxml.presentation.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import com.example.myvktestxml.databinding.FragmentVideoPlayerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerFragment : BaseFragment<FragmentVideoPlayerBinding>() {

    private lateinit var videoUrl: String
    private lateinit var exoPlayer: ExoPlayer

    private var playerPosition: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        videoUrl = VideoPlayerFragmentArgs.Companion.fromBundle(requireArguments()).videoUrl
        exoPlayer = ExoPlayer.Builder(requireContext()).build()

        if (savedInstanceState != null) {
            playerPosition = savedInstanceState.getLong("player_position", 0)
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVideoPlayerBinding {
        return FragmentVideoPlayerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
    }

    override fun onResume() {
        super.onResume()
        initExoPlayer()
    }

    override fun onPause() {
        super.onPause()
        playerPosition = exoPlayer.currentPosition
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong("player_position", exoPlayer.currentPosition)
    }

    override fun onDestroy() {
        super.onDestroy()
        exoPlayer.release()
    }

    private fun initExoPlayer() {
        videoUrl.let {
            val mediaItem = MediaItem.fromUri(it)
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
            exoPlayer.seekTo(playerPosition)
        }
        binding.playerView.player = exoPlayer
    }

    private fun initToolbar() {
        updateToolbarVisibility()
        binding.videoPlayerFragmentToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun updateToolbarVisibility() {
        val configuration = resources.configuration
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.videoPlayerFragmentToolbar.visibility = View.GONE
        } else {
            binding.videoPlayerFragmentToolbar.visibility = View.VISIBLE
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateToolbarVisibility()
    }


}