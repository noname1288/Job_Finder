package com.example.jobfinder.domain.session

data class UserSession (
    val userId: Int,
    val fullName: String,
    val email: String,
    val role: String,
    val token: String? = null,
)