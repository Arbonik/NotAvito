package com.castprogramms.karma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.ui.navigateUp
import com.castprogramms.karma.data.Result
import com.castprogramms.karma.network.Repository
import org.koin.android.ext.android.inject

class RegistrationActivity : AppCompatActivity() {
    private val repository : Repository by inject()
    val liveDataTimer = MutableLiveData(false)
    private val timer = object : CountDownTimer(1000, 1000){
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            liveDataTimer.postValue(true)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registr)
        timer.start()
        supportActionBar?.hide()
        liveDataTimer.observe(this, {
            if (it) {
                repository.userLiveData.observe(this) {
                    if (it != null && it is Result.Enter) {
                        Log.e("size", it.toString())
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}