package com.castprogramms.karma.network

import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.User

interface ServiceFireStoreInterface {

    fun addService(service: Service)

    fun deleteService(service: Service)

    fun getService(id: String): MutableLiveData<Resource<Service>>

    fun getAllService(): MutableLiveData<Resource<List<Pair<String,Service>>>>

    fun getAllUserServices(id: String): MutableLiveData<Resource<List<Service>>>

    fun getAllOtherUserServices(id: String): MutableLiveData<Resource<List<Pair<String,Service>>>>
}