package com.castprogramms.karma.network

import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.tools.Service
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class ServiceFireStore: ServiceFireStoreInterface {
    private val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .build()
    private val fireStore = FirebaseFirestore.getInstance().apply {
        firestoreSettings = settings
    }

    override fun addService(service: Service) {
        fireStore.collection(SERVICES_TAG)
            .document()
            .set(service)
    }

    override fun deleteService(service: Service) {
        fireStore.collection(SERVICES_TAG)
//            .whereEqualTo("")
    }

    override fun getAllService(): MutableLiveData<Resource<List<Service>>> {
        val mutableLiveData = MutableLiveData<Resource<List<Service>>>(null)
        fireStore.collection(SERVICES_TAG)
            .addSnapshotListener { value, error ->
                if (value != null){
                    mutableLiveData.postValue(Resource.Loading())
                    mutableLiveData.postValue(Resource.Success(value.toObjects(Service::class.java)))
                }
                else{
                    mutableLiveData.postValue(Resource.Error(error?.message))
                }
            }
        return mutableLiveData
    }

    companion object{
        const val SERVICES_TAG = "services"
    }
}