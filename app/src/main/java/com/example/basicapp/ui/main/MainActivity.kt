package com.example.basicapp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.basicapp.R
import com.example.basicapp.databinding.ActivityMainBinding
import com.example.basicapp.ui.settings.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var navView: NavigationView
    private lateinit var titleTextView: TextView
    private lateinit var toolbar: Toolbar

    private var currentFragmentTag: String = TAG_NEWS_FEED

    companion object {
        private const val TAG_NEWS_FEED = "news_feed"
        private const val TAG_SETTINGS = "settings"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.constraintLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        drawerLayout = binding.main
        navView = binding.navView
        bottomNav = binding.bottomNavigation
        titleTextView = binding.textView
        toolbar = binding.toolbar

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            loadFragment(NewsFeedFragment(), TAG_NEWS_FEED)
            bottomNav.selectedItemId = R.id.newsFeed
            navView.setCheckedItem(R.id.newsFeed)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.newsFeed -> navigateTo(TAG_NEWS_FEED)
                R.id.settings -> navigateTo(TAG_SETTINGS)
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.newsFeed -> {
                    navigateTo(TAG_NEWS_FEED)
                    true
                }
                R.id.settings -> {
                    navigateTo(TAG_SETTINGS)
                    true
                }
                else -> false
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            updateNavigationVisibility()
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                when {
                    drawerLayout.isDrawerOpen(GravityCompat.START) -> {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    supportFragmentManager.backStackEntryCount > 0 -> {
                        supportFragmentManager.popBackStack()
                    }
                    currentFragmentTag != TAG_NEWS_FEED -> {
                        navigateTo(TAG_NEWS_FEED)
                        bottomNav.selectedItemId = R.id.newsFeed
                    }
                    else -> {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        })
    }

    private fun updateNavigationVisibility() {
        val hasBackStack = supportFragmentManager.backStackEntryCount > 0

        if (hasBackStack) {
            bottomNav.visibility = View.GONE
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            toggle.isDrawerIndicatorEnabled = false
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener {
                supportFragmentManager.popBackStack()
            }
            titleTextView.text = "News Detail"
        } else {
            bottomNav.visibility = View.VISIBLE
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            toggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            toggle.syncState()
            toolbar.setNavigationOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
            }
            // Restore title based on current fragment
            titleTextView.text = if (currentFragmentTag == TAG_NEWS_FEED) "News Feed" else "Settings"
        }
    }

    private fun navigateTo(tag: String) {
        if (currentFragmentTag == tag) {
            if (tag == TAG_NEWS_FEED) {
                (supportFragmentManager.findFragmentByTag(tag) as? NewsFeedFragment)?.scrollToTop()
            }
            return
        }

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val fragment: Fragment
        val title: String
        val menuItemId: Int

        when (tag) {
            TAG_NEWS_FEED -> {
                fragment = NewsFeedFragment()
                title = "News Feed"
                menuItemId = R.id.newsFeed
            }
            TAG_SETTINGS -> {
                fragment = SettingsFragment()
                title = "Settings"
                menuItemId = R.id.settings
            }
            else -> return
        }

        loadFragment(fragment, tag)
        currentFragmentTag = tag
        titleTextView.text = title

        if (bottomNav.selectedItemId != menuItemId) {
            bottomNav.selectedItemId = menuItemId
        }
        navView.setCheckedItem(menuItemId)
    }

    private fun loadFragment(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .commit()
    }
}