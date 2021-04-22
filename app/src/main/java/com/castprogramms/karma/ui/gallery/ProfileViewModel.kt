package com.castprogramms.karma.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.castprogramms.karma.network.Repository

class ProfileViewModel(private val repository: Repository) : ViewModel() {
    fun getUser(id:String) = repository.getUser(id)
}