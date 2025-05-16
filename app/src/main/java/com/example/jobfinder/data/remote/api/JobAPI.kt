package com.example.jobfinder.data.remote.api

import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.CreateJobRequest
import com.example.jobfinder.data.remote.dto.response.CreateJobResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface JobAPI {
    //create job
    @POST("/jobs")
    suspend fun createJob(@Body request: CreateJobRequest): Response<BaseResponse<CreateJobResponse>>

}