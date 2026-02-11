package com.example.basicapp.ui.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.basicapp.R
import com.example.basicapp.databinding.ActivityMainBinding
import com.example.basicapp.ui.settings.SettingsFragment
import com.example.basicapp.ui.userlist.UserListFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var userListFragment: UserListFragment? = null
    private var settingsFragment: SettingsFragment? = null

    //Yo aaile kun fragment xa bhanera track garnalai
    private var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //Viewbinding lai initialize garreko
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Hamro root meaning layout tree ko top (constrained layout) lai display garne
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.constraintLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        
        // Toolbar ko default text lai hide gareko
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //Fragment lai initialize
        if (savedInstanceState == null) {
            userListFragment = UserListFragment()
            showFragment(userListFragment!!, "Users", "UserList")
        } else {
            //Tag le kun fragment chaliraxa ani replace garne bhanera track garne
            userListFragment = supportFragmentManager.findFragmentByTag("UserList") as? UserListFragment
            settingsFragment = supportFragmentManager.findFragmentByTag("Settings") as? SettingsFragment
            currentFragment = userListFragment ?: settingsFragment
        }

        // Bottom Nav ko code
        // setOnItemSelectedListener le bottom nav ma thicheko track garxa
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            // "When" bhaneko switch statement jastai
            when (item.itemId) {
                R.id.nav_home -> {
                    if (currentFragment is UserListFragment) {
                        (currentFragment as UserListFragment).scrollToTop()
                    } else {
                        // Find existing fragment or create new one
                        val fragment = supportFragmentManager.findFragmentByTag("UserList") as? UserListFragment
                            ?: UserListFragment().also { userListFragment = it }
                        showFragment(fragment, "Users", "UserList")
                    }
                    // Sync with drawer navigation
                    binding.navView.setCheckedItem(R.id.nav_home)
                    
                    true  // Return true to indicate we handled the click
                }
                R.id.nav_settings -> {
                    val fragment = supportFragmentManager.findFragmentByTag("Settings") as? SettingsFragment
                        ?: SettingsFragment().also { settingsFragment = it }
                    showFragment(fragment, "Settings", "Settings")

                    // Sync with drawer navigation
                    binding.navView.setCheckedItem(R.id.nav_settings)
                    
                    true
                }
                else -> false  // Unknown item, don't handle it
            }
        }

        //sidwbar
        val toggle = ActionBarDrawerToggle(
            this,
            binding.main,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.main.addDrawerListener(toggle)
        toggle.syncState() //screen rotation bhayo bhane toggle lai sync garne

        //sidebar
        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    if (currentFragment is UserListFragment) {
                        (currentFragment as UserListFragment).scrollToTop()
                    } else {
                        val fragment = supportFragmentManager.findFragmentByTag("UserList") as? UserListFragment
                            ?: UserListFragment().also { userListFragment = it }
                        showFragment(fragment, "Users", "UserList")
                    }
                    binding.bottomNavigation.selectedItemId = R.id.nav_home
                    binding.main.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_settings -> {
                    val fragment = supportFragmentManager.findFragmentByTag("Settings") as? SettingsFragment
                        ?: SettingsFragment().also { settingsFragment = it }
                    showFragment(fragment, "Settings", "Settings")
                    binding.bottomNavigation.selectedItemId = R.id.nav_settings
                    binding.main.closeDrawer(GravityCompat.START)
                    true
                }
                else -> {
                    binding.main.closeDrawer(GravityCompat.START)
                    false
                }
            }
        }
    }

    private fun showFragment(fragment: Fragment, title: String, tag: String) {
        // Update toolbar title
        supportActionBar?.title = title
        
        // Start a fragment transaction
        val transaction = supportFragmentManager.beginTransaction()
        
        // Hide the currently visible fragment (if any)
        currentFragment?.let {
            transaction.hide(it)
        }
        
        // Check if this fragment is already added using the FragmentManager
        val existingFragment = supportFragmentManager.findFragmentByTag(tag)
        
        if (existingFragment == null) {
            // Fragment doesn't exist yet, add it
            transaction.add(R.id.fragment_container, fragment, tag)
        } else {
            // Fragment exists, just show it
            transaction.show(existingFragment)
            // Update currentFragment to the existing one, not the passed parameter
            currentFragment = existingFragment
            transaction.commit()
            return
        }
        
        // Commit the transaction
        transaction.commit()
        
        // Update current fragment reference
        currentFragment = fragment
    }

    //nav bar ani side bar hide/show
    fun showNavigation() {
        binding.bottomNavigation.visibility = android.view.View.VISIBLE
        binding.main.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED)

        // pheri menu enable
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        val toggle = ActionBarDrawerToggle(
            this,
            binding.main,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.main.addDrawerListener(toggle)
        toggle.syncState()
    }

    fun hideNavigation() {
        binding.bottomNavigation.visibility = android.view.View.GONE
        binding.main.setDrawerLockMode(androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        // Menu ko satta back button enable garne
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            supportFragmentManager.popBackStack()
        }
    }

   //Back button tool bar ko
    override fun onSupportNavigateUp(): Boolean {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
            return true
        }
        return super.onSupportNavigateUp()
    }
}
