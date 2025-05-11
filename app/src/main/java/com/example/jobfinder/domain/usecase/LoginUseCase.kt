package com.example.jobfinder.domain.usecase

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.response.LoginUserResponse
import com.example.jobfinder.domain.repository.UserRepository

class LoginUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(
        username: String,
        password: String
    ): NetworkResult<LoginUserResponse> = repository.login(username, password)
}

