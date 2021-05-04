package com.castprogramms.karma.ui.profileuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.karma.network.Repository
import com.castprogramms.karma.network.Resource
import com.castprogramms.karma.tools.Score
import com.castprogramms.karma.tools.Service

class ProfileUserViewModel(private val repository: Repository): ViewModel(){
    fun getUserServices(id: String) = repository.getAllOtherUserServices(id)
    fun getUserData(id: String) = repository.getUser(id)
}