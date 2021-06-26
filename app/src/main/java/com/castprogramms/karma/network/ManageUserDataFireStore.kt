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
                        addScore(id, Score(score, "Регестрация"))
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

    override fun addScore(id: String, score: Score): MutableLiveData<Resource<String>> {
        val mutableLiveData = MutableLiveData<Resource<String>>(Resource.Loading())
        var scores = listOf<HashMap<String, String>>()
        var isContains = false
        fireStore.collection(USERS_TAG).document(id)
            .get()
            .addOnSuccessListener {
                if (it.get("scores") != null) {
                    scores = (it.get("scores")) as List<HashMap<String, String>>
                    isContains = (scores.find { it.get("idSender") == score.idSender && it.get("idService") == score.idService} != null)
                }
            }.continueWith {
                if (!isContains){
                    fireStore.collection(USERS_TAG)
                        .document(id)
                        .update("scores", FieldValue.arrayUnion(score))
                    mutableLiveData.postValue(Resource.Success("Вы успешно поставили оценку"))
                }
                else
                    mutableLiveData.postValue(Resource.Error("Вы уже ставили оценку"))
                if (score.idService != "Регестрация"){
                    fireStore.collection(USERS_TAG)
                        .document(score.idSender)
                        .update("scores", FieldValue.arrayUnion(Score(10, score.idService, score.idSender)))
                }
            }
        return mutableLiveData
    }

    companion object{
        const val USERS_TAG = "users"
    }
}