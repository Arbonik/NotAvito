package com.castprogramms.karma.network

import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.tools.Fields
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.User
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
        var id = ""
        fireStore.collection(SERVICES_TAG)
            .whereEqualTo(Fields.SERVICES_COST.desc, service.cost)
            .whereEqualTo(Fields.SERVICES_DATA_TIME.desc, service.dataTime)
            .whereEqualTo(Fields.SERVICES_DESC.desc, service.desc)
            .whereEqualTo(Fields.SERVICES_ID_AUTHOR.desc, service.idAuthor)
            .whereEqualTo(Fields.SERVICES_NAME.desc, service.name)
            .get().addOnCompleteListener {
                if (it.isSuccessful){
                    it.result?.documents?.forEach {
                        id = it.id
                    }
                }
            }.continueWith {
                fireStore.collection(SERVICES_TAG)
                    .document(id)
                    .delete()
            }
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

    override fun getAllUserServices(id: String): MutableLiveData<Resource<List<Service>>> {
        val mutableLiveData = MutableLiveData<Resource<List<Service>>>(null)
        fireStore.collection(SERVICES_TAG)
            .whereEqualTo("idAuthor", id)
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
        const val USERS_TAG = "users"
    }
}