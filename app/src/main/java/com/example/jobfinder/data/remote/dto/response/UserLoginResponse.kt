package com.example.jobfinder.data.remote.dto.response
/*
* "id": 7,
    "fullName": "quang",
    "email": "quang2@gmail.com",
    "role": "RECRUITER",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxdWFuZzJAZ21haWwuY29tIiwiaWF0IjoxNzQ1NTczNTcxLCJleHAiOjE3NDU1NzcxNzF9.hImPbUvhvGcCIhFiR2FLSlvGQB4SZPVMgVsVLEJ3K8o"
* */
data class UserLoginResponse(
    val id: Int,
    val fullName: String,
    val email: String,
    val role: String,
    val token: String
)