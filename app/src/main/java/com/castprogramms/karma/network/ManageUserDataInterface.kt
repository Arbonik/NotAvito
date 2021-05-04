package com.castprogramms.karma.network

import androidx.lifecycle.MutableLiveData
import com.castprogramms.karma.tools.Score
import com.castprogramms.karma.tools.User

interface ManageUserDataInterface {
    fun addUser(user: User, id: String)

    fun getUser(id: String): MutableLiveData<Resource<Pair<String, User>>>

    fun addScore(id: String, score: Score)
}