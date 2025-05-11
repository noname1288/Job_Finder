package com.example.jobfinder.data.remote.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.core.safeApiCall
import com.example.jobfinder.data.remote.RetrofitHelper
import com.example.jobfinder.data.remote.api.UserAPI
import com.example.jobfinder.data.remote.dto.request.UserLoginRequest
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.response.LoginUserResponse
import com.example.jobfinder.data.remote.mapper.toRegisterUserRequest
import com.example.jobfinder.domain.entity.Recruiter1
import com.example.jobfinder.domain.repository.UserRepository

class UserRepositoryImpl(private val api: UserAPI = RetrofitHelper.userAPI) : UserRepository {
    override suspend fun login(
        username: String,
        password: String
    ): NetworkResult<LoginUserResponse> {
        return safeApiCall {
            api.login(UserLoginRequest(username,password))
        }.let { result ->
            when (result) {
                is NetworkResult.Success -> {
//                    val body = result.data as BaseResponse<LoginUserResponse>
                    // No need for casting - the type is already correct
                    if (result.data.code == 200 && result.data.data != null) {
                        NetworkResult.Success(result.data.data)
                    } else {
                        NetworkResult.Error(result.data.message ?: "Login failed")
                    }
                }

                is NetworkResult.Error -> result
                is NetworkResult.Loading -> result // thường không trả loading ở repo, nhưng để đây vi dùng ở UseCase
            }
        }
    }

    override suspend fun register(
       user: Recruiter1
    ): NetworkResult<Unit> {
        return when (val response = safeApiCall { api.registerUser(user.toRegisterUserRequest()) }){
            is NetworkResult.Success ->{
                //Kiểm tra code nếu là 200 thì trả về success
                if (response.data.code == 201){
                    NetworkResult.Success(Unit)
                } else {
                    NetworkResult.Error(response.data.message ?: "Registration failed")
                }
            }
            is NetworkResult.Error -> response
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }
}