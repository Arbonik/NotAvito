package com.castprogramms.karma.network

import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.User

interface ServiceFireStoreInterface {

    fun addService(service: Service)

    fun deleteService(service: Service)

    fun getAllService(): MutableLiveData<Resource<List<Service>>>

    fun getUser(id: String): MutableLiveData<Resource<User>>
}