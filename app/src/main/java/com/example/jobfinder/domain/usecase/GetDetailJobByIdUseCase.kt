package com.example.jobfinder.domain.usecase

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.mapper.toJob
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.domain.repository.JobRepository

class GetDetailJobByIdUseCase(private val repository: JobRepository) {
    suspend operator fun invoke(jobId: Int): NetworkResult<Job> {
        return when (val res = repository.getDetailJob(jobId)) {
            is NetworkResult.Error -> res
            is NetworkResult.Success -> {
                val result = res.data

                if (result.code == 200 && result.data != null){
                    NetworkResult.Success(result.data.toJob())
                } else {
                    NetworkResult.Error(result.message?: "Fetch detail job failed")
                }

            }
        }
    }
}