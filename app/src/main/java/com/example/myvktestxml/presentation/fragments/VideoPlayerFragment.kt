package com.example.myvktestxml.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.myvktestxml.databinding.FragmentVideoPlayerBinding


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


}