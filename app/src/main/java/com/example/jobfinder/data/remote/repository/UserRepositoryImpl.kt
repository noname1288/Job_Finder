package com.example.jobfinder.data.remote.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.core.safeApiCall
import com.example.jobfinder.data.remote.RetrofitHelper
import com.example.jobfinder.data.remote.api.UserAPI
import com.example.jobfinder.data.remote.dto.request.UserLoginRequest
import com.example.jobfinder.data.remote.dto.response.BaseResponse
import com.example.jobfinder.data.remote.dto.response.UserLoginResponse
import com.example.jobfinder.domain.repository.UserRepository

class UserRepositoryImpl(private val api: UserAPI = RetrofitHelper.userAPI) : UserRepository {
    override suspend fun login(
        username: String,
        password: String
    ): NetworkResult<UserLoginResponse> {
        return safeApiCall {
            api.login(email = username, password= password)
        }.let { result ->
            when (result) {
                is NetworkResult.Success -> {
                    val body = result.data as BaseResponse<UserLoginResponse>
                    if (body.status == "success" && body.data != null) {
                        NetworkResult.Success(body.data)
                    } else {
                        NetworkResult.Error(body.message ?: "Login failed")
                    }
                }
                is NetworkResult.Error -> result
                is NetworkResult.Loading -> result // thường không trả loading ở repo, nhưng để đây nếu bạn dùng ở UseCase
            }
        }
    }
}