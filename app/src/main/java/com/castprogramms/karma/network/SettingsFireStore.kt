package com.castprogramms.karma.network

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class SettingsFireStore: SettingsInterface {
    private val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .build()
    private val fireStore = FirebaseFirestore.getInstance().apply {
        firestoreSettings = settings
    }

    override fun getInfoAboutCreators(): MutableLiveData<Resource<String>> {
        val mutableLiveData = MutableLiveData<Resource<String>>(Resource.Loading())
        fireStore.collection(SETTINGS_TAG)
            .document(INFO_TAG)
            .addSnapshotListener { value, error ->
                if (value != null){
                    mutableLiveData.postValue(Resource.Success(value.getString("about").toString()))
                }
                else{
                    mutableLiveData.postValue(Resource.Error(error?.message))
                }
            }
        return mutableLiveData
    }

    companion object{
        val SETTINGS_TAG = "settings"
        val INFO_TAG = "info"
    }
}