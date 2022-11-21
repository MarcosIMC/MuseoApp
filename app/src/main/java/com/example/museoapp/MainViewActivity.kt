package com.example.museoapp

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.museoapp.databinding.ActivityMainViewBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar

class MainViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainViewBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Floatting Button
        binding.appBarMain.fab.setOnClickListener{ view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        //Bottom Navigation
        val navBottomView: BottomNavigationView = binding.appBarMain.navView
        navController = findNavController(R.id.nav_host_fragment_activity_main_view)

        //Burger Menu
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navTopView: NavigationView = binding.navigationView

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_user, R.id.navigation_information,
                R.id.museumEventActivity
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navTopView.setupWithNavController(navController)
        navBottomView.setupWithNavController(navController)
    }

    //Acciones del Burger Menu (Abrir y Cerrar)
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main_view)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}