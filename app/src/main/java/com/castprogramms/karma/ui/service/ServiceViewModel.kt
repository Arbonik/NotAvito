package com.castprogramms.karma.ui.service

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.karma.network.Repository
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.tools.Score
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.User

class ServiceViewModel(private val repository: Repository): ViewModel() {
    var liveDataService = MutableLiveData<Resource<Service>>(null)
    var liveDataUserData = MutableLiveData<Resource<Pair<String,User>>>(null)
    fun loadService(id: String) {
        liveDataService = repository.getService(id)
    }

    fun loadUserData(id: String) {
        repository.getUser(id).observeForever {
            liveDataUserData.postValue(it)
        }
    }

    fun addScoreUser(id: String, score: Score) = repository.addScoreUser(id, score)

    fun getCurrentUser() = repository.user

    fun checkClicks(idAuthor: String, idService: String, idSender: String) =
        repository.checkClicks(idAuthor, idService, idSender)
}