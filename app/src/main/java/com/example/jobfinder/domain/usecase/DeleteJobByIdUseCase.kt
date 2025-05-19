package com.example.jobfinder.domain.usecase

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.domain.repository.JobRepository

class DeleteJobByIdUseCase(private val repository: JobRepository) {
    suspend operator fun invoke(jobId: Int) : NetworkResult<BaseResponse<Unit>> {
         return when(val res = repository.deleteJobById(jobId)){
            is NetworkResult.Error -> res
            is NetworkResult.Success -> {
                val result = res.data

                if (result.code == 200 && result.data == null){
                    NetworkResult.Success(result)
                } else NetworkResult.Error(result.message ?: "Delete fail")
            }
        }
    }
}