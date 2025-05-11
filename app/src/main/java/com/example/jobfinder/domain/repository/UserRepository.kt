package com.example.jobfinder.domain.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.response.LoginUserResponse
import com.example.jobfinder.domain.entity.Recruiter1

interface UserRepository {
    suspend fun login(username: String, password: String): NetworkResult<LoginUserResponse>
    suspend fun register(user: Recruiter1) : NetworkResult<Unit>

}