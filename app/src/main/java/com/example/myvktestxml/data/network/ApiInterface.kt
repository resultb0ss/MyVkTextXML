package com.example.myvktestxml.data.network

import com.example.myvktestxml.domain.models.ReceivedVideos
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiInterface {

    @GET("videos")
    suspend fun getVideos(
        @Header("Authorization") token: String
    ): Response<ReceivedVideos> {
        return this.getVideos(token)
    }
}