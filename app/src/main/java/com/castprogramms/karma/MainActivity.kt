package com.castprogramms.karma

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.castprogramms.karma.data.Result
import com.castprogramms.karma.databinding.NavHeaderMainBinding
import com.castprogramms.karma.network.Repository
import com.castprogramms.karma.network.Resource
import com.google.android.material.navigation.NavigationView
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private val repository: Repository by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navLayout = navView.getHeaderView(0)
        val binding = NavHeaderMainBinding.bind(navLayout)
        repository.userLiveData.observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Auth -> {
                        repository.getUser(it.data.uid).observe(this) {
                            when(it){
                                is Resource.Error -> {}
                                is Resource.Loading -> {}
                                is Resource.Success -> {
                                    if (it.data != null)
                                        binding.nameUser.text = it.data.second.name
                                }
                            }
                        }
                        binding.textView.text = it.data.email
                    }
                    is Result.Enter -> {
                        repository.getUser(it.data.uid).observe(this) {
                            when(it){
                                is Resource.Error -> {

                                }
                                is Resource.Loading -> {

                                }
                                is Resource.Success -> {
                                    if (it.data != null)
                                        binding.nameUser.text = it.data.second.getFullName()
                                }
                            }
                        }
                        binding.textView.text = it.data.email
                    }
                }
            }
        }
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(
               R.id.profileFragment, R.id.allServicesFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}