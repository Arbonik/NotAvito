package com.castprogramms.karma.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.tools.Service
import com.castprogramms.karma.tools.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Repository(private val serviceFireStore: ServiceFireStore,
                 private val manageUserDataFireStore: ManageUserDataFireStore) {
    private val fireBaseAuthenticator = FirebaseAuth.getInstance()
    val userLiveData = MutableLiveData<FirebaseUser>(null)
    var user : FirebaseUser? = null

    init {
        user = fireBaseAuthenticator.currentUser
        if (user != null)
            userLiveData.postValue(user)
    }

    fun login(email: String, password: String){
        if (user == null){
            fireBaseAuthenticator.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        userLiveData.postValue(it.result!!.user)
                    } else {
                        fireBaseAuthenticator.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                            if (it.isSuccessful){
                                userLiveData.postValue(it.result?.user)
                            }
                        }
                    }
                }
            }
    }
    fun addService(service: Service) = serviceFireStore.addService(service)
    fun getAllServices() = serviceFireStore.getAllService()

    fun getUser(id: String) = manageUserDataFireStore.getUser(id)
}