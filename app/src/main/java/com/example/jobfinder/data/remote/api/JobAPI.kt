package com.example.jobfinder.data.remote.api

import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.CreateJobRequest
import com.example.jobfinder.data.remote.dto.response.CreateJobResponse
import com.example.jobfinder.data.remote.dto.response.GetJobsInHomePageResponse
import com.example.jobfinder.data.remote.dto.response.JobTemp2
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface JobAPI {
    //create job
    @POST("/recruiter/post-job")
    suspend fun createJob(
        @Query("recruiterId") recruiterId: Int,
        @Body createJobRequest: CreateJobRequest
    ): Response<BaseResponse<CreateJobResponse>>

    //get info in home page
    @GET("/recruiter/home")
    suspend fun getJobsInHome(@Query("recruiterId") recruiterId:Int, @Query("month") month:Int ): Response<BaseResponse<GetJobsInHomePageResponse>>

    //get all jobs : use in workspace page
    @GET("/recruiter/jobs/{recruiterId}")
    suspend fun getAllJobs(@Path("recruiterId") recruiterId:Int): Response<BaseResponse<List<JobTemp2>>>

    //get detail job
    @GET("/recruiter/job/{jobId}")
    suspend fun getDetailJob(@Path("jobId") jobId:Int): Response<BaseResponse<JobTemp2>>

    //delete job by Id
    @DELETE("/recruiter/delete-job/{jobId}")
    suspend fun deleteJobById(@Path("jobId") jobId:Int): Response<BaseResponse<Unit>>

    //update job status

}