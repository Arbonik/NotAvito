package com.castprogramms.karma.ui.allServices

import androidx.lifecycle.ViewModel
import com.castprogramms.karma.network.Repository
import com.castprogramms.karma.tools.Score
import com.castprogramms.karma.tools.Service

class ServicesViewModel(private val repository: Repository): ViewModel() {
    fun getAllServices() = repository.getAllServices()
    fun addScoreUser(id:String, score: Score) = repository.addScoreUser(id, score)
}