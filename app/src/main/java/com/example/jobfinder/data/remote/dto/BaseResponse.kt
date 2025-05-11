package com.example.jobfinder.data.remote.dto

//cấu trúc response
/*
* status:
* message:
* data: {}
* */
data class BaseResponse<T>(
    val code:Int,
    val message: String?,
    val data: T?
)