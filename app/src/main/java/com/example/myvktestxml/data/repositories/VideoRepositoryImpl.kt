package com.example.myvktestxml.data.repositories

import com.example.myvktestxml.BuildConfig
import com.example.myvktestxml.data.local.VideoDao
import com.example.myvktestxml.data.network.ApiInterface
import com.example.myvktestxml.domain.models.Resource
import com.example.myvktestxml.domain.models.VideoEntity
import com.example.myvktestxml.domain.repositories.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val videoDao: VideoDao
) : VideoRepository {

    override suspend fun getVideos(): Flow<Resource<List<VideoEntity>>> {
        return flow {
            emit(Resource.Loading())

            val cachedVideos = videoDao.getAllVideos()
            if (cachedVideos.isNotEmpty()) {
                emit(Resource.Success(cachedVideos))
            }

            try {
                val token = BuildConfig.API_TOKEN
                val response = apiInterface.getVideos("Bearer $token")

                if (response.isSuccessful) {

                    videoDao.deleteAllVideos()

                    val videos = checkNotNull(response.body()).data.map { it.toVideoEntity() }
                    videoDao.insertVideos(videos)
                    emit(Resource.Success(videos))
                } else {
                    emit(Resource.Error("Data upload error"))
                }

            } catch (e: Exception) {
                emit(Resource.Error("Network error: ${e.message}"))

                if (cachedVideos.isNotEmpty()) {
                    emit(Resource.Success(cachedVideos))
                }
            }
        }
    }

}