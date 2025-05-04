package com.example.jobfinder.presentation.login

import android.util.Log
import androidx.lifecycle.*
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.response.UserLoginResponse
import com.example.jobfinder.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _username = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    val username: LiveData<String> = _username
    val password: LiveData<String> = _password

    fun updateNameChange(newUsername: String)  { _username.value = newUsername }
    fun updatePasswordChange(newPassword: String) { _password.value = newPassword }

    private val _loginState = MutableStateFlow<NetworkResult<UserLoginResponse>?>(null)
    val loginState: StateFlow<NetworkResult<UserLoginResponse>?> = _loginState.asStateFlow()

    /** Đẩy sự kiện login – tự lấy username/password hiện có */
    fun login() {
        val user = _username.value.orEmpty()
        val pass = _password.value.orEmpty()
        Log.i("LoginViewModel", "login() called with username: $user, password: $pass")

        viewModelScope.launch {
            _loginState.value = NetworkResult.Loading
            _loginState.value = loginUseCase(user, pass)
        }
    }
}
