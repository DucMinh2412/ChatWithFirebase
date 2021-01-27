package com.example.chatwithfirebase.ui.welcome

import android.content.Intent
import com.example.chatwithfirebase.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class WelcomeViewModel @Inject constructor() : BaseViewModel() {

    companion object {
        // error
        const val ERROR_LOGIN = 10

        // success
        const val LOGIN_SUCCESS = 9
    }

    fun saveUserAndAutoLogin() {
        setLoading(true)
        if (firebaseAuthRepository.getCurrentUser() != null) {
            uiEventLiveData.value = LOGIN_SUCCESS
        }
        else{
            setLoading(false)
            uiEventLiveData.value = ERROR_LOGIN
        }
    }
}