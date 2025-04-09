package com.example.jobfinder.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _username = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    val username : LiveData<String> = _username
    val password : LiveData<String> = _password

    fun updateNameChange(newUsername: String){
        _username.value= newUsername
    }
    fun updatePasswordChange(newPassword: String){
        _password.value= newPassword
    }
}