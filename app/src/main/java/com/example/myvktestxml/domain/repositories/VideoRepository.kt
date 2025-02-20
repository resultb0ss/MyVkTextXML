package com.example.myvktestxml.domain.repositories

import com.example.myvktestxml.domain.models.Resource
import com.example.myvktestxml.domain.models.VideoEntity
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    suspend fun getVideos(): Flow<Resource<List<VideoEntity>>>
}