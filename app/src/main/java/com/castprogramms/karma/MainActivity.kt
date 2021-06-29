package com.castprogramms.karma

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.MutableLiveData
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
import com.castprogramms.karma.tools.Score
import com.google.android.material.navigation.NavigationView
import org.koin.android.ext.android.inject
import java.util.*

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
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        ) // проверка на наличие разрешений
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.CALL_PHONE), 101)
            }
        repository.userLiveData.observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Auth -> {
                        repository.getUser(it.data?.uid!!).observe(this) {
                            when (it) {
                                is Resource.Error -> {
                                }
                                is Resource.Loading -> {
                                }
                                is Resource.Success -> {
                                    if (it.data != null) {
                                        binding.scores.text =
                                            countScores(it.data.second.scores)
                                        binding.nameUser.text = it.data.second.name
                                    }
                                }
                            }
                        }
                        binding.textView.text = it.data.email
                    }
                    is Result.Enter -> {
                        repository.getUser(it.data?.uid!!).observe(this) {
                            when (it) {
                                is Resource.Error -> {
                                }
                                is Resource.Loading -> {
                                }
                                is Resource.Success -> {
                                    if (it.data != null) {
                                        binding.scores.text =
                                            countScores(it.data.second.scores)
                                        binding.nameUser.text = it.data.second.getFullName()
                                    }
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
               R.id.news, R.id.myService, R.id.allServicesFragment, R.id.donats, R.id.faq), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun countScores(list: List<Score>): String {
        var summary = 0
        list.forEach { summary += it.value }
        if (summary > 0)
            return resources.getString(R.string.scores) + " +" + summary.toString()
        else
            return resources.getString(R.string.scores) + " " + summary.toString()
    }
}