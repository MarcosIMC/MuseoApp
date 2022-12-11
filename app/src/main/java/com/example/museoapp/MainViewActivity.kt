package com.example.museoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.museoapp.ViewModel.MainViewModel
import com.example.museoapp.databinding.ActivityMainViewBinding
import com.example.museoapp.ui.home.HomeViewModel
import com.google.android.material.navigation.NavigationView
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainViewBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val mainViewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Floatting Button
        binding.appBarMain.fab.setOnClickListener{ view ->
            mainViewModel.initScanner(this)
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
                R.id.museumEventActivity, R.id.userProfileFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navTopView.setupWithNavController(navController)
        navBottomView.setupWithNavController(navController)
    }

    //Cuando se recibe el result del Scanner
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_SHORT).show()
            }else {
                //Toast.makeText(this, "El valor es: ${result.contents}", Toast.LENGTH_SHORT).show()
                mainViewModel.getElement(result.contents)
                mainViewModel.list.observe(this) {
                    if (it != null){
                        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
                        homeViewModel.itemClicked(0, it, this)
                    }
                }
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    //Acciones del Burger Menu (Abrir y Cerrar)
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main_view)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}