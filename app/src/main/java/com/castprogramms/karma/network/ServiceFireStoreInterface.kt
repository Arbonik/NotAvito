package com.castprogramms.karma.network

import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.tools.Service

interface ServiceFireStoreInterface {

    fun addService(service: Service)

    fun deleteService(service: Service)

    fun getAllService(): MutableLiveData<Resource<List<Service>>>
}