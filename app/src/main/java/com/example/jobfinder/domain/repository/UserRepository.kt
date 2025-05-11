package com.example.jobfinder.domain.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.LoginUserRequest
import com.example.jobfinder.data.remote.dto.request.RegisterUserRequest
import com.example.jobfinder.data.remote.dto.response.LoginUserResponse

interface UserRepository {
    suspend fun login(request: LoginUserRequest): NetworkResult<BaseResponse<LoginUserResponse>>
    suspend fun register(request: RegisterUserRequest) : NetworkResult<BaseResponse<Unit>>
}