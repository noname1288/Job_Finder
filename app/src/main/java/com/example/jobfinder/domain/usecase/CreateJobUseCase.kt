package com.example.jobfinder.domain.usecase

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.CreateJobRequest
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.domain.repository.JobRepository

class CreateJobUseCase(private val jobRepository: JobRepository) {
    suspend operator fun invoke(job: CreateJobRequest): NetworkResult<BaseResponse<Job>> {
        return jobRepository.createJob(job)
    }
}