package com.example.myvktestxml.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "videos")
data class VideoEntity(
    @PrimaryKey val id: String = "0",
    val title: String,
    val thumbnail: String,
    val description: String,
    val mp4Url: String
)