package com.example.jobfinder.domain.usecase

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.request.LoginUserRequest
import com.example.jobfinder.data.remote.dto.response.LoginUserResponse
import com.example.jobfinder.domain.repository.UserRepository

class LoginUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(
        request: LoginUserRequest
    ): NetworkResult<LoginUserResponse> {
        return when(val result = repository.login(request)){
            is NetworkResult.Error -> result
            is NetworkResult.Success -> {
                val res = result.data

                if (res.code == 200 && res.data != null){
                    NetworkResult.Success(res.data)
                } else NetworkResult.Error(res.message ?: "Login failed")
            }
        }
    }
}

