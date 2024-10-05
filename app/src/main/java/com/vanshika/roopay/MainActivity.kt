package com.vanshika.roopay

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vanshika.roopay.RechargeResponse
import com.vanshika.roopay.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var apiService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        initCall();

        // Bottom Navigation
        setupBottomNavigation(navController)

        itemClickListener();

    }

    private fun itemClickListener() {
        binding.ivRefresh.setOnClickListener {
            Toast.makeText(this, "API Calling ...", Toast.LENGTH_SHORT).show()
            callRechargeApi()
        }
        binding.ivMenu.setOnClickListener {
            drawerLayout.openDrawer(binding.navigationView)
        }
    }

    private fun initCall() {
        drawerLayout = binding.drawer

        // Navigation Drawer
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //  AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.settlementFragment,
                R.id.reportFragment,
                R.id.menuFragment
            ), drawerLayout
        )

        //  NavController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.navigationView, navController)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://supay.in/") // Base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

    }

    private fun setupBottomNavigation(navController: NavController) {
        val bottomNav: BottomNavigationView = binding.bottomNavigation
        NavigationUI.setupWithNavController(bottomNav, navController)

        bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    navController.navigate(R.id.homeFragment)
                    drawerLayout.closeDrawer(binding.navigationView)
                    true
                }

                R.id.navigation_settlements -> {
                    navController.navigate(R.id.settlementFragment)
                    drawerLayout.closeDrawer(binding.navigationView)
                    true
                }

                R.id.navigation_reports -> {
                    navController.navigate(R.id.reportFragment)
                    drawerLayout.closeDrawer(binding.navigationView)
                    true
                }

                R.id.navigation_menu -> {
                    navController.navigate(R.id.menuFragment)
                    drawerLayout.closeDrawer(binding.navigationView)
                    true
                }

                else -> false
            }
        }
    }


    override fun onSupportNavigateUp(): Boolean {

        return NavigationUI.navigateUp(
            navController,
            appBarConfiguration
        ) || super.onSupportNavigateUp()
    }

    private fun callRechargeApi() {
        val call = apiService.getRechargeDetails(
            memberId = "9876543210",
            apiPassword = "1234",
            apiPin = "1234",
            number = "998988200"
        )

        call.enqueue(object : Callback<RechargeResponse> {
            override fun onResponse(
                call: Call<RechargeResponse>,
                response: Response<RechargeResponse>
            ) {
                if (response.isSuccessful) {
                    val rechargeResponse = response.body()
                    Log.d("API_SUCCESS", "Response body =============" + rechargeResponse)

                    rechargeResponse?.let {

                        val message = "Status: ${it.status}, Message: ${it.message}"
                        Log.d("API_SUCCESS", message)
                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                    } ?: run {
                        Log.d("API_ERROR", "Response body is null")
                        Toast.makeText(
                            this@MainActivity,
                            "Response body is null",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.d("API_ERROR", "Failed response: $errorMessage")
                    Toast.makeText(this@MainActivity, "Failed: $errorMessage", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<RechargeResponse>, t: Throwable) {
                Log.d("API_ERROR", "API call failed: ${t.message}")
                Toast.makeText(
                    this@MainActivity,
                    "API call failed: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
