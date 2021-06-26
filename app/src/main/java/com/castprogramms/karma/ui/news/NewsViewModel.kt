package com.castprogramms.karma.ui.news

import androidx.lifecycle.ViewModel
import com.castprogramms.karma.network.Repository
import com.castprogramms.karma.tools.New

class NewsViewModel(private val repository: Repository): ViewModel() {
    fun getTitleNew() = repository.getTitleNew()
    fun getBodyNew() = repository.getBodyNew()
}