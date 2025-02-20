package com.example.myvktestxml.domain.models

data class ReceivedVideos(
    val context: List<Any>,
    val `data`: List<Data>,
    val pagination: Pagination
)

data class Source(
    val type: String,
    val uri: String
)

data class Pagination(
    val currentPage: Int,
    val currentPageItems: Int,
    val itemsTotal: Int,
    val links: List<Link>,
    val pageSize: Int,
    val pagesTotal: Int
)

data class Link(
    val rel: String,
    val uri: String
)

data class Data(
    val assets: Assets,
    val createdAt: String,
    val deletesAt: Any,
    val description: String,
    val discarded: Boolean,
    val discardedAt: Any,
    val language: Any,
    val languageOrigin: String,
    val metadata: List<Any>,
    val mp4Support: Boolean,
    val panoramic: Boolean,
    val `public`: Boolean,
    val publishedAt: String,
    val source: Source,
    val tags: List<Any>,
    val title: String,
    val updatedAt: String,
    val videoId: String
) {
    fun toVideoEntity(): VideoEntity {
        return VideoEntity(
            id = videoId,
            title = title,
            thumbnail = assets.thumbnail,
            description = description,
            mp4Url = assets.mp4
        )
    }
}

data class Assets(
    val hls: String,
    val iframe: String,
    val mp4: String,
    val player: String,
    val thumbnail: String
)