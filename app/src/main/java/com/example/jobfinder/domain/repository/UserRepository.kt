package com.example.jobfinder.domain.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.response.UserLoginResponse

interface UserRepository {
    suspend fun login(username: String, password: String): NetworkResult<UserLoginResponse>
}