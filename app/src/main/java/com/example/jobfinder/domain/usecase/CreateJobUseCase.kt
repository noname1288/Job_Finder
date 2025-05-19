package com.example.jobfinder.domain.usecase

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.CreateJobRequest
import com.example.jobfinder.data.remote.dto.response.CreateJobResponse
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.domain.repository.JobRepository

class CreateJobUseCase(private val jobRepository: JobRepository) {
    suspend operator fun invoke(recruiterId: Int, createJobRequest: CreateJobRequest): NetworkResult<CreateJobResponse> {
        return when(val res = jobRepository.createJob(recruiterId, createJobRequest)){
            is NetworkResult.Error -> res
            is NetworkResult.Success -> {
                val result = res.data

                if (result.code == 201 && result.data != null){
                    NetworkResult.Success(result.data)
                }else
                    NetworkResult.Error(result.message ?: "Create job fail")
            }
        }
    }
}