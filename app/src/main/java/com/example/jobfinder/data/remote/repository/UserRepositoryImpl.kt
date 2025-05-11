package com.example.jobfinder.data.remote.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.core.safeApiCall
import com.example.jobfinder.data.remote.api.UserAPI
import com.example.jobfinder.data.remote.dto.request.LoginUserRequest
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.RegisterUserRequest
import com.example.jobfinder.data.remote.dto.response.LoginUserResponse
import com.example.jobfinder.data.remote.mapper.toRegisterUserRequest
import com.example.jobfinder.domain.entity.Recruiter1
import com.example.jobfinder.domain.repository.UserRepository

class UserRepositoryImpl(private val api: UserAPI) : UserRepository {
    override suspend fun login(
        request: LoginUserRequest
    ): NetworkResult<BaseResponse<LoginUserResponse>> {
        return safeApiCall {
            api.login(request)
        }
    }

    override suspend fun register(
       request: RegisterUserRequest
    ): NetworkResult<BaseResponse<Unit>> {
        return safeApiCall {
            api.registerUser(request)
        }
    }
}