package com.example.myvktestxml

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myvktestxml.data.local.VideoDao
import com.example.myvktestxml.data.network.ApiInterface
import com.example.myvktestxml.data.repositories.VideoRepositoryImpl
import com.example.myvktestxml.domain.models.VideoEntity
import com.example.myvktestxml.presentation.MainActivity
import com.example.myvktestxml.presentation.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(AppModule::class)
class MainListScreenTest {

    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Inject
    lateinit var videoRepository: VideoRepositoryImpl

    @Inject
    lateinit var videoDao: VideoDao

    @Inject
    lateinit var apiInterface: ApiInterface

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        hiltRule.inject()

        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun testCaching() = runBlocking {
        val fakeVideos = listOf(
            VideoEntity(
                id = "1",
                title = "Video 1",
                description = "Description 1",
                thumbnail = "url1",
                mp4Url = "mp4url1"
            ),
            VideoEntity(
                id = "2",
                title = "Video 2",
                description = "Description 2",
                thumbnail = "url2",
                mp4Url = "mp4url2"
            )
        )


        videoDao.insertVideos(fakeVideos)

        val cachedVideos = videoDao.getAllVideos()
        println("Cached videos: $cachedVideos")
        assertEquals(2, cachedVideos.size)
    }

}