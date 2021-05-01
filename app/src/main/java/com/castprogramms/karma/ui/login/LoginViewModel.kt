package com.castprogramms.karma.ui.login

import android.util.Patterns
import androidx.lifecycle.*
import com.castprogramms.karma.data.LoginRepository
import com.castprogramms.karma.data.Result

import com.castprogramms.karma.R
import com.castprogramms.karma.network.Repository

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String, lifecycleOwner: LifecycleOwner) {
        // can be launched in a separate asynchronous job
        repository.login(username, password)
        repository.userLiveData.observe(lifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Auth -> {
                        _loginResult.value =
                            LoginResult(LoggedInUserView(it.data?.providerId!!, it.data.uid, true))
                    }
                    is Result.Enter -> {
                        _loginResult.value =
                            LoginResult(LoggedInUserView(it.data?.providerId!!, it.data.uid, false))
                    }
                    is Result.Fail -> {
                        _loginResult.postValue(
                            LoginResult(error = it.message)
                        )
                    }
                }
            }
        }
//        if (result is Result.Success) {
//            _loginResult.value =
//                LoginResult(success = LoggedInUserView(result.data.displayName, result.data.userId))
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).find()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}