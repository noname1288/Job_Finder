package com.example.jobfinder.data.remote.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.RetrofitHelper
import com.example.jobfinder.data.remote.api.JobAPI
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.CreateJobRequest
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.domain.repository.JobRepository

class JobRepositoryImpl(private val jobApi: JobAPI) : JobRepository {
    override suspend fun createJob(job: CreateJobRequest): NetworkResult<BaseResponse<Job>> {
        TODO("Not yet implemented")
    }
}