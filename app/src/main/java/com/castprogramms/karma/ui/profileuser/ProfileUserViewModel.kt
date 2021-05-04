package com.castprogramms.karma.ui.profileuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.karma.network.Repository
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.tools.Service

class ProfileUserViewModel(private val repository: Repository): ViewModel(){
    fun getUserServices(id: String) = repository.getAllUserServices(id)
    fun deleteService(service: Service) = repository.deleteService(service)
}