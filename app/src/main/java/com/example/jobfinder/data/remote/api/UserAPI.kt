package com.example.jobfinder.data.remote.api

import com.example.jobfinder.data.remote.dto.request.UserLoginRequest
import com.example.jobfinder.data.remote.dto.response.BaseResponse
import com.example.jobfinder.data.remote.dto.response.UserLoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface UserAPI {
    //LOGIN
    @POST("/auth/login")
    suspend fun login(
       @Query("email")email: String,
       @Query("password")password: String
    ) : Response<BaseResponse<UserLoginResponse>>
}