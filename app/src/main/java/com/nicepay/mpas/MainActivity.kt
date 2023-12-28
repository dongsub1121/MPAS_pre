package com.nicepay.mpas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nicepay.mpas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val navView = findViewById<BottomNavigationView>(R.id.nav_view)

        val appBarConfiguration: AppBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_sales, R.id.navigation_payment, R.id.navigation_orders
        ).build()

        val navController = findNavController(this, R.id.nav_host_fragment_activity_main)
        setupActionBarWithNavController(this, navController, appBarConfiguration)
        setupWithNavController(binding!!.navView, navController)
    }
}