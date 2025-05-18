package com.example.jobfinder.data.remote.mapper

import com.example.jobfinder.data.remote.dto.request.RegisterUserRequest
import com.example.jobfinder.domain.entity.Recruiter1

fun Recruiter1.toRegisterUserRequest() : RegisterUserRequest{
    return RegisterUserRequest(
        fullName = this.fulName,
        email = email,
        password = password,
        role = role_recrutier
    )
}