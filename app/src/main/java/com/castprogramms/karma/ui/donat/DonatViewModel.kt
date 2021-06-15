package com.castprogramms.karma.ui.donat

import androidx.lifecycle.ViewModel
import com.castprogramms.karma.network.Repository

class DonatViewModel(private val repository: Repository) : ViewModel() {
    fun getInfoAboutDonat() = repository.getInfoAboutDonat()
}