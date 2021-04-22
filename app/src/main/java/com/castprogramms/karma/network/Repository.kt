package com.castprogramms.karma.network

import com.castprogramms.karma.tools.Service

class Repository(private val serviceFireStore: ServiceFireStore) {
    fun addService(service: Service) = serviceFireStore.addService(service)
    fun getAllServices() = serviceFireStore.getAllService()
    fun getUser(id: String) = serviceFireStore.getUser(id)
}