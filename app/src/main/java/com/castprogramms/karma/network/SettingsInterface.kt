package com.castprogramms.karma.network

import androidx.lifecycle.MutableLiveData

interface SettingsInterface {
    fun getInfoAboutCreators(): MutableLiveData<Resource<String>>
    fun getInfoAboutDonat(): MutableLiveData<Resource<String>>
}