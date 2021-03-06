package com.castprogramms.karma.ui.addServices

import androidx.lifecycle.ViewModel
import com.castprogramms.karma.network.Repository
import com.castprogramms.karma.tools.Service

class AddServiceViewModel(private val repository: Repository): ViewModel() {
    fun addService(service: Service) = repository.addService(service)
    fun minusKarma(id: String, name: String) = repository.minusKarma(id, name)
    fun getUser() = repository.user
    fun checkHaveNeedData(id: String) = repository.checkHaveNeedKarma(id)
}