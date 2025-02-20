package com.example.myvktestxml.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myvktestxml.domain.models.Resource
import com.example.myvktestxml.domain.models.VideoEntity
import com.example.myvktestxml.domain.usecases.GetVideoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getVideoUseCase: GetVideoUseCase
) : ViewModel() {
    private var _videos =
        MutableStateFlow<Resource<List<VideoEntity>>>(Resource.Success(emptyList()))
    val videos: StateFlow<Resource<List<VideoEntity>>> = _videos

    init {
        loadVideos()
    }

    fun loadVideos() {
        viewModelScope.launch {
            getVideoUseCase.execute().collect { resource ->
                _videos.value = resource
            }
        }
    }
}