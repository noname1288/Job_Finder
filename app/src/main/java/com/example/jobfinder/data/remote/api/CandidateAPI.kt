package com.example.jobfinder.data.remote.api

import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.response.ApplicationResponse
import com.example.jobfinder.data.remote.dto.response.CandidateManagementPageDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface CandidateAPI {
    // get applications by jobId
    @GET("/recruiter/applications/{jobPostingId}")
    suspend fun getApplicationByJobId (@Path("jobPostingId") jobId: Int): Response<BaseResponse<List<ApplicationResponse>>>

    //get info for candidate management page
    @GET("/recruiter/candidates/view")
    suspend fun getCandidateInPage(
        @Query("recruiterId") recruiterId : Int,
        @Query("date") date : String,
    ) : Response<BaseResponse<CandidateManagementPageDTO>>

    //approve or reject applicatoin
    @PUT("/recruiter/update-application-status/{applicationId}")
    suspend fun updateApplicationStatus(
        @Path("applicationId") applicationId : Int,
        @Query("status") status : String,
    ) : Response<BaseResponse<Unit>>
}