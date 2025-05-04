package com.example.jobfinder.data.remote.dto.response

//cấu trúc response
/*
* status:
* message:
* data: {}
* */
data class BaseResponse<T>(
    val status: String,
    val message: String?,
    val data: T?
)