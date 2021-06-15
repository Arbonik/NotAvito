package com.castprogramms.karma.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.tools.Score
import com.castprogramms.karma.tools.User
import com.google.firebase.firestore.FieldValue
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
            .continueWith {
                var score = 0
                fireStore.collection(SettingsFireStore.SETTINGS_TAG)
                    .document(SettingsFireStore.INFO_TAG)
                    .get().addOnSuccessListener {
                        if (it.getLong("startCoin") != null)
                            score = it.getLong("startCoin")!!.toInt()
                    }.continueWith {
                        addScore(id, Score(score))
                    }
            }
    }

    override fun getUser(id: String): MutableLiveData<Resource<Pair<String, User>>> {
        val mutableLiveData = MutableLiveData<Resource<Pair<String,User>>>(null)
        fireStore.collection(USERS_TAG)
            .document(id)
            .addSnapshotListener { value, error ->
                if (value != null){
                    mutableLiveData.postValue(Resource.Loading())
                    Log.e("auth", id + value.data.toString())
                    mutableLiveData.postValue(Resource.Success(id to value.toObject(User::class.java)!!))
                }
                else{
                    mutableLiveData.postValue(Resource.Error(error?.message))
                }
            }
        return mutableLiveData
    }

    override fun addScore(id: String, score: Score) {
        fireStore.collection(USERS_TAG)
            .document(id)
            .update("scores", FieldValue.arrayUnion(score))
    }

    companion object{
        const val USERS_TAG = "users"
    }
}