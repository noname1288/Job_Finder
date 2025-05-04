package com.example.jobfinder.domain.usecase

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.response.UserLoginResponse
import com.example.jobfinder.data.remote.repository.UserRepositoryImpl
import com.example.jobfinder.domain.repository.UserRepository

class LoginUseCase(private val repo: UserRepository) {
    suspend operator fun invoke(username: String, password: String)
            : NetworkResult<UserLoginResponse> =
        repo.login(username, password)
}
