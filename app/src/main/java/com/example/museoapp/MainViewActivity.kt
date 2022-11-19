package com.example.museoapp

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.TaskStackBuilder
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.museoapp.databinding.ActivityMainViewBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainViewBinding
    private lateinit var appTopBar: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBarMain.fab.setOnClickListener{ view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //Bottom Navigation
        val navView: BottomNavigationView = binding.appBarMain.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main_view)

        //Burger Menu
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navigationView: NavigationView = binding.navigationView
        appTopBar = AppBarConfiguration(navController.graph, drawerLayout)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        NavigationUI.setupWithNavController(navigationView, navController)

        /*val
        val navBurgerView: NavigationView = binding.navigationView
        val appTopBar = AppBarConfiguration(
            setOf(
                R.id.museumEvent
            ),drawerLayout
        )
        setupActionBarWithNavController(navController, appTopBar)
        navBurgerView.setupWithNavController(navController)*/

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.navigation_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main_view)
        return navController.navigateUp(appTopBar) || super.onSupportNavigateUp()
    }
}