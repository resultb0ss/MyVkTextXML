package com.example.myvktestxml.domain.usecases

import com.example.myvktestxml.domain.models.Resource
import com.example.myvktestxml.domain.models.VideoEntity
import com.example.myvktestxml.domain.repositories.VideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideoUseCase @Inject constructor(
    private val videoRepository: VideoRepository
) {
    suspend fun execute(): Flow<Resource<List<VideoEntity>>> = videoRepository.getVideos()
}