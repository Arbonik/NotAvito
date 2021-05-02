package com.castprogramms.karma.ui.service

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.karma.network.Repository
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.User

class ServiceViewModel(private val repository: Repository): ViewModel() {
    var liveDataService = MutableLiveData<Resource<Service>>(null)
    var liveDataUserData = MutableLiveData<Resource<Pair<String,User>>>(null)
    fun loadService(id: String) {
        liveDataService = repository.getService(id)
    }

    fun loadUserData(id: String) {
        liveDataUserData = repository.getUser(id)
    }
}