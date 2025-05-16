package com.example.jobfinder.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.local.UserSessionManager
import com.example.jobfinder.data.remote.dto.request.LoginUserRequest
import com.example.jobfinder.data.remote.dto.request.RegisterUserRequest
import com.example.jobfinder.data.remote.dto.response.LoginUserResponse
import com.example.jobfinder.domain.usecase.LoginUseCase
import com.example.jobfinder.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    application: Application,
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase
) : AndroidViewModel(application) {
    //state login
    private val _stateLogin = MutableStateFlow(LoginState())
    val stateLogin: StateFlow<LoginState> = _stateLogin

    //register login
    private val _stateRegister = MutableStateFlow(RegisterState())
    val stateRegister: StateFlow<RegisterState> = _stateRegister

    //check if user is logged in
    val isUserAlreadyLoggedIn = MutableStateFlow(false)

    init {
        viewModelScope.launch {
            UserSessionManager.isLoggedInFlow.collect { loggedIn ->
                isUserAlreadyLoggedIn.value = loggedIn
            }
        }
    }
    //event handler for form field changes
    fun onLoginEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                _stateLogin.value = _stateLogin.value.copy(username = event.email)
            }
            is LoginEvent.PasswordChanged -> {
                _stateLogin.value = _stateLogin.value.copy(password = event.password)
            }
            LoginEvent.Login -> {
                login()
            }
        }
    }

    //event handler for form field changes
    fun onRegisterEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.FullNameChanged -> {
                _stateRegister.update { it.copy(fullName = event.fullName) }
            }

            is RegisterEvent.EmailChanged -> {
                _stateRegister.update { it.copy(email = event.email) }
            }

            is RegisterEvent.PasswordChanged -> {
                _stateRegister.update { it.copy(password = event.password) }
            }

            is RegisterEvent.Register -> {
                registerUser()
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            //set loading state
            _stateLogin.value = _stateLogin.value.copy(isLoading = true, errorMessage = null)

            //use the login usecase
            val request = LoginUserRequest(stateLogin.value.username, stateLogin.value.password )
            val result = loginUseCase(request)

            //update state based on the result
            when (result){
                is NetworkResult.Error -> {
                    _stateLogin.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
                is NetworkResult.Success -> {
                    //store user data
                    val userPrefs = result.data
                    UserSessionManager.setUserData(userPrefs)
                    //handle when state change
                    _stateLogin.update {
                        it.copy(isLoading = false, isSuccess = true, userData = result.data)
                    }
                }
            }
        }
    }

    private fun registerUser() {
        viewModelScope.launch {
            //set loading state
            _stateRegister.update { it.copy(isLoading = true, errorMessage = null) }

            //use the registration usecase
            val request = RegisterUserRequest(
                email = _stateRegister.value.email,
                password = _stateRegister.value.password,
                fullName = _stateRegister.value.fullName,
                role = _stateRegister.value.role
            )
            val result = registerUseCase(request)

            //update state based on the result
            when(result){
                else -> {
                    _stateRegister.update { it.copy(isLoading = false, isSuccess = true) }
                }
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            UserSessionManager.clearUserData()
            UserSessionManager.userIdFlow.collect { id ->
                Log.d("AuthViewModel", "Logout successful and userId = $id")
            }
        }
    }

    fun onNavigatedToHome() {
        _stateLogin.update { it.copy(isSuccess = false) }
    }
    fun onNavigatedToRegister() {
        _stateRegister.update { it.copy(isSuccess = false) }
    }
}


//UIState for Login Screen
data class LoginState(
    val username: String = "",
    val password: String = "",
    val userData: LoginUserResponse? = null,
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    override val isSuccess: Boolean = false
) : BaseUIState()

//Events that can be triggered from the UI
sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    object Login : LoginEvent()
}

//UI State for Register Screen
data class RegisterState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val role: String = "RECRUITER", //default role
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    override val isSuccess: Boolean = false
) : BaseUIState(isLoading, errorMessage, isSuccess)

//Events that can be triggered from the UI
sealed class RegisterEvent {
    data class FullNameChanged(val fullName: String) : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    object Register : RegisterEvent()
}