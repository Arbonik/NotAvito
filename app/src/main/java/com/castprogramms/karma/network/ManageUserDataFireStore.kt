package com.castprogramms.karma.network

import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.tools.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class ManageUserDataFireStore: ManageUserDataInterface {
    private val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .build()
    private val fireStore = FirebaseFirestore.getInstance().apply {
        firestoreSettings = settings
    }

    override fun addUser(user: User, id: String){
        fireStore.collection(USERS_TAG)
            .document(id)
            .set(user)
    }

    override fun getUser(id: String): MutableLiveData<Resource<User>> {
        val mutableLiveData = MutableLiveData<Resource<User>>(null)
        fireStore.collection(ServiceFireStore.USERS_TAG)
            .document(id)
            .addSnapshotListener { value, error ->
                if (value != null){
                    mutableLiveData.postValue(Resource.Loading())
                    mutableLiveData.postValue(Resource.Success(value.toObject(User::class.java)!!))
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