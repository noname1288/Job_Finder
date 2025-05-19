package com.example.jobfinder.domain.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.CreateJobRequest
import com.example.jobfinder.data.remote.dto.response.CreateJobResponse
import com.example.jobfinder.data.remote.dto.response.GetJobsInHomePageResponse
import com.example.jobfinder.data.remote.dto.response.JobTemp2

interface JobRepository {
    suspend fun createJob(recruiterId: Int, createJobRequest: CreateJobRequest) : NetworkResult<BaseResponse<CreateJobResponse>>

    suspend fun getJobsInHomePage(recruiterId: Int, month: Int) : NetworkResult<GetJobsInHomePageResponse>

    suspend fun getAllJobs(recruiterId: Int) : NetworkResult<BaseResponse<List<JobTemp2>>>

}