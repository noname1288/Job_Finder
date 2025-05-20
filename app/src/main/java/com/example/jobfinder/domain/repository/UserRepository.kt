package com.example.jobfinder.domain.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.LoginUserRequest
import com.example.jobfinder.data.remote.dto.request.RegisterUserRequest
import com.example.jobfinder.data.remote.dto.response.JobSeekerDTO
import com.example.jobfinder.data.remote.dto.response.JobSeekerDTO2
import com.example.jobfinder.data.remote.dto.response.LoginUserResponse

interface UserRepository {
    // đăng nhập
    suspend fun login(request: LoginUserRequest): NetworkResult<BaseResponse<LoginUserResponse>>

    // đăng ký
    suspend fun register(request: RegisterUserRequest) : NetworkResult<BaseResponse<Unit>>

    //lấy thông tin người ứng tuyển
    suspend fun getProfile(seekId: Int) : NetworkResult<BaseResponse<JobSeekerDTO2>>

}