package com.castprogramms.karma.network

import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.data.Result
import com.castprogramms.karma.tools.New
import com.castprogramms.karma.tools.Score
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Repository(private val serviceFireStore: ServiceFireStore,
                 private val manageUserDataFireStore: ManageUserDataFireStore,
                 private val newsFireStore: NewsFireStore) {
    private val fireBaseAuthenticator = FirebaseAuth.getInstance()
    val userLiveData = MutableLiveData<Result<FirebaseUser>>(null)
    var user : FirebaseUser? = null

    init {
        user = fireBaseAuthenticator.currentUser
        if (user != null)
            userLiveData.postValue(Result.Enter(user!!))
    }

    fun login(email: String, password: String){
        if (user == null){
            userLiveData.postValue(Result.Loading())
            fireBaseAuthenticator.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        userLiveData.postValue(Result.Enter(it.result?.user!!))
                        user = it.result?.user
                    }
                }.addOnFailureListener {
                    userLiveData.postValue(Result.Fail(it.message))
                }
            }
    }

    fun addUser(user: User, email: String, password: String){
        var id = ""
        userLiveData.postValue(Result.Loading())
        fireBaseAuthenticator.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                id = it.user!!.uid
                userLiveData.postValue(Result.Auth(it.user!!))
            }
            .addOnFailureListener {
                userLiveData.postValue(Result.Fail(it.message))
            }
            .continueWith {
                if (id != "")
                    manageUserDataFireStore.addUser(user, id)
            }
    }

    fun addScoreUser(id: String, score: Score) = manageUserDataFireStore.addScore(id, score)
    fun addService(service: Service) = serviceFireStore.addService(service)
    fun getAllServices() = serviceFireStore.getAllService()
    fun getAllUserServices(id: String) = serviceFireStore.getAllUserServices(id)
    fun getUser(id: String) = manageUserDataFireStore.getUser(id)
    fun deleteService(service: Service) = serviceFireStore.deleteService(service)
    fun getService(id: String) = serviceFireStore.getService(id)
    fun getAllOtherUserServices(id: String) = serviceFireStore.getAllOtherUserServices(id)


    //news
    fun addNew(new: New) = newsFireStore.addNew(new)
    fun getAllNews() = newsFireStore.getAllNews()
}