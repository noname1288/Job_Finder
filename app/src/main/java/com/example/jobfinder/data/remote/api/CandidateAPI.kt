package com.example.jobfinder.data.remote.api

import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.response.ApplicationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CandidateAPI {
    @GET("/recruiter/applications/pending/{jobPostingId}")
    suspend fun getApplicationByJobId (@Path("jobPostingId") jobId: Int): Response<BaseResponse<List<ApplicationResponse>>>
}