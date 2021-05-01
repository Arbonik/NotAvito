package com.castprogramms.karma.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.data.Result
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Repository(private val serviceFireStore: ServiceFireStore,
                 private val manageUserDataFireStore: ManageUserDataFireStore) {
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
//                        fireBaseAuthenticator.signInWithEmailAndPassword(email, password).addOnCompleteListener {
//                            if (it.isSuccessful){
//                                userLiveData.postValue(Result.Enter(it.result?.user!!))
//                            }
//                            else
//                                userLiveData.postValue(Result.Fail(it.exception?.message.toString()))
//                        }
                }.addOnFailureListener {
                    userLiveData.postValue(Result.Fail(it.message))
                }
            }
    }

    fun addUser(user: User, email: String, password: String){
        var id = ""
        fireBaseAuthenticator.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    if (it.result != null){
                        id = it.result?.user!!.uid
                        userLiveData.postValue(Result.Auth(it.result?.user!!))
                        this.user = it.result?.user
                    }
                }
            }.continueWith {
                if (id != "")
                    manageUserDataFireStore.addUser(user, id)
            }
    }

    fun addService(service: Service) = serviceFireStore.addService(service)
    fun getAllServices() = serviceFireStore.getAllService()
    fun getAllUserServices(id: String) = serviceFireStore.getAllUserServices(id)
    fun getUser(id: String) = manageUserDataFireStore.getUser(id)
    fun deleteService(service: Service) = serviceFireStore.deleteService(service)
    fun getService(id: String) = serviceFireStore.getService(id)
}