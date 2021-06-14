package com.castprogramms.karma.ui.info

import androidx.lifecycle.ViewModel
import com.castprogramms.karma.network.Repository

class InfoViewModel(private val repository: Repository): ViewModel() {
    fun getInfo() = repository.getInfo()
}