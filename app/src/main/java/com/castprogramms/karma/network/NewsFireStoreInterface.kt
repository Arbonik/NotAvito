package com.castprogramms.karma.network

import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.tools.New

interface NewsFireStoreInterface {
    fun addNew(new: New)

    fun getAllNews(): MutableLiveData<Resource<List<New>>>
}