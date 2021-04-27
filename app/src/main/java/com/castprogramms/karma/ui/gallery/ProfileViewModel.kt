package com.castprogramms.karma.ui.gallery

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.karma.data.Result
import com.castprogramms.karma.network.Repository
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.User

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    fun getUser() = repository.userLiveData
    fun getUserData(): MutableLiveData<Resource<Pair<String, User>>> {
        var mutableLiveData = MutableLiveData<Resource<Pair<String,User>>>(null)
        var id = ""
        val user = getUser().value
        when(user){
            is Result.Enter ->{
                id = user.data.uid
            }
            is Result.Auth ->{
                id = user.data.uid
            }
        }
        if (id != "")
            mutableLiveData = repository.getUser(id)

        return mutableLiveData
    }

    fun getAllUserServices(id: String) = repository.getAllUserServices(id)
    fun deleteService(service: Service) = repository.deleteService(service)
}