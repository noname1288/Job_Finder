package com.example.jobfinder.data.remote.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.core.safeApiCall
import com.example.jobfinder.data.remote.api.JobAPI
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.CreateJobRequest
import com.example.jobfinder.data.remote.dto.response.GetJobsInHomePageResponse
import com.example.jobfinder.data.remote.dto.response.JobTemp2
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.domain.repository.JobRepository

class JobRepositoryImpl(private val jobApi: JobAPI) : JobRepository {
    override suspend fun createJob(job: CreateJobRequest): NetworkResult<BaseResponse<Job>> {
        TODO("Not yet implemented")
    }

    override suspend fun getJobsInHomePage(recruiterId: Int, month: Int): NetworkResult<GetJobsInHomePageResponse> {
        safeApiCall {
            jobApi.getJobsInHome(recruiterId, month)
        }.run {
            return when (this){
                is NetworkResult.Error -> NetworkResult.Error(this.message)
                is NetworkResult.Success-> {
                    if (this.data.code == 200 && this.data.data != null){
                        NetworkResult.Success(this.data.data)
                    } else {
                        NetworkResult.Error(this.data.message ?: "Fetch data in Home fail")
                    }
                }
            }
        }
    }

    override suspend fun getAllJobs(recruiterId: Int): NetworkResult<BaseResponse<List<JobTemp2>>> {
        return safeApiCall {
            jobApi.getAllJobs(recruiterId)
        }
    }
}