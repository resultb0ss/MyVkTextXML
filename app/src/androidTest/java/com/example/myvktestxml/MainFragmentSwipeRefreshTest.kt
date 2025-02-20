package com.example.myvktestxml

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.myvktestxml.presentation.MainActivity
import org.hamcrest.core.IsNot.not
import org.junit.Rule
import org.junit.Test

class MainFragmentSwipeRefreshTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSwipeToRefresh() {

        onView(withId(R.id.mainFragmentSwipeRefreshLayout))
            .perform(ViewActions.swipeDown())

        onView(withId(R.id.mainFragmentProgressBar))
            .check(ViewAssertions.matches(isDisplayed()))

        onView(withId(R.id.mainFragmentProgressBar))
            .check(ViewAssertions.matches(not(isDisplayed())))
    }
}
