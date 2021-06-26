package com.castprogramms.karma.network

import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.tools.New
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

class NewsFireStore: NewsFireStoreInterface {
    private val settings = FirebaseFirestoreSettings.Builder()
        .setPersistenceEnabled(true)
        .build()
    private val fireStore = FirebaseFirestore.getInstance().apply {
        firestoreSettings = settings
    }

    override fun addNew(new: New) {
        fireStore.collection(NEWS_TAG)
            .document()
            .set(new)
    }

    override fun getAllNews(): MutableLiveData<Resource<List<New>>> {
        val mutableLiveData = MutableLiveData<Resource<List<New>>>(Resource.Loading())
        fireStore.collection(NEWS_TAG)
            .addSnapshotListener { value, error ->
                if (value != null){
                    mutableLiveData.postValue(Resource.Success(value.toObjects(New::class.java)))
                }
                else
                    mutableLiveData.postValue(Resource.Error(error?.message))
            }
        return mutableLiveData
    }

    fun getTitleNew(): MutableLiveData<Resource<String>> {
        val mutableLiveData = MutableLiveData<Resource<String>>(Resource.Loading())
        fireStore.collection(NEWS_TAG)
            .document("new")
            .addSnapshotListener { value, error ->
                if (value != null)
                    mutableLiveData.postValue(Resource.Success(value.getString("title").toString()))
                else
                    mutableLiveData.postValue(Resource.Error(error?.message))
            }
        return mutableLiveData
    }

    fun getBodyNew(): MutableLiveData<Resource<String>> {
        val mutableLiveData = MutableLiveData<Resource<String>>(Resource.Loading())
        fireStore.collection(NEWS_TAG)
            .document("new")
            .addSnapshotListener { value, error ->
                if (value != null)
                    mutableLiveData.postValue(Resource.Success(value.getString("body").toString()))
                else
                    mutableLiveData.postValue(Resource.Error(error?.message))
            }
        return mutableLiveData
    }

    companion object{
        const val NEWS_TAG = "news"
    }
}