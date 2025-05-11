package com.example.jobfinder.data.remote.dto.request

data class RegisterUserRequest(
    val email: String,
    val password: String,
    val fullName: String,
    val role: String
)