package com.example.basicapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.basicapp.ui.main.MainActivity
import org.hamcrest.Matchers.allOf

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testBottomNavigationSwitchesFragments() {
        onView(withId(R.id.userRecycler)).check(matches(isDisplayed()))
        onView(allOf(
            withId(R.id.nav_settings),
            isDescendantOfA(withId(R.id.bottom_navigation))
        )).perform(click())
        onView(withId(R.id.settingsRecyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun testDrawerNavigation() {
        onView(withId(R.id.main)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_home))
        Thread.sleep(300)
        onView(withId(R.id.main)).check(matches(isClosed()))
        onView(withId(R.id.userRecycler)).check(matches(isDisplayed()))
    }
}