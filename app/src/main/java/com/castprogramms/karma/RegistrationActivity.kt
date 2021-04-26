package com.castprogramms.karma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import com.castprogramms.karma.network.Repository
import org.koin.android.ext.android.inject

class RegistrationActivity : AppCompatActivity() {
    private val repository : Repository by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registr)
        repository.userLiveData.observe(this, {
            if (it != null ){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}