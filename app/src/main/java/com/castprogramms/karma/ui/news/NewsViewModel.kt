package com.castprogramms.karma.ui.news

import androidx.lifecycle.ViewModel
import com.castprogramms.karma.network.Repository
import com.castprogramms.karma.tools.New

class NewsViewModel(private val repository: Repository): ViewModel() {
    fun getAllNews() = repository.getAllNews()
    fun addNews(new: New) = repository.addNew(new)
}