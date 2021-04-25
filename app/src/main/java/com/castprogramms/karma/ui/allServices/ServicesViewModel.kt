package com.castprogramms.karma.ui.allServices

import androidx.lifecycle.ViewModel
import com.castprogramms.karma.network.Repository
import com.castprogramms.karma.tools.Service

class ServicesViewModel(private val repository: Repository): ViewModel() {
    fun addService(service: Service) = repository.addService(service)
    fun getAllServices() = repository.getAllServices()
}