package com.example.jobfinder.data.remote.dto.response

import com.example.jobfinder.data.remote.dto.BaseResponse


//define inner data type
data class LoginUserResponse(
    val id: Int,
    val fullName: String,
    val email: String,
    val role: String,
    val token: String
)

