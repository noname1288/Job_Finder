package com.example.jobfinder.domain.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.CreateJobRequest
import com.example.jobfinder.domain.entity.Job

interface JobRepository {
    suspend fun createJob(job: CreateJobRequest) : NetworkResult<BaseResponse<Job>>
}