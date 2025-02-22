package com.example.myvktestxml

import androidx.navigation.fragment.NavHostFragment
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.myvktestxml.presentation.MainActivity
import com.example.myvktestxml.presentation.fragments.VideoPlayerFragmentArgs
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class VideoPlayerFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun testVideoPlayerFragmentNavigation() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        val videoUrl = "https://example.com/video.mp4"
        activityScenario.onActivity { activity ->

            val navHostFragment = activity.supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
            val navController = navHostFragment.navController

            navController.navigate(
                R.id.action_mainFragment_to_videoPlayerFragment,
                VideoPlayerFragmentArgs(videoUrl).toBundle()
            )
        }

        onView(withId(R.id.playerView)).check(matches(isDisplayed()))
        activityScenario.close()
    }
}