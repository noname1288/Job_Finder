package com.example.jobfinder.data.remote.api

import com.example.jobfinder.data.remote.dto.request.UserLoginRequest
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.RegisterUserRequest
import com.example.jobfinder.data.remote.dto.response.RegisterUserResponse
import com.example.jobfinder.data.remote.dto.response.LoginUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {
    //LOGIN
    @POST("/auth/login")
    suspend fun login(
       @Body request: UserLoginRequest
    ): Response<BaseResponse<LoginUserResponse>>

    //Resgiter
    @POST("/auth/register")
    suspend fun registerUser(
        @Body request: RegisterUserRequest
    ) : Response<BaseResponse<Nothing>>
}