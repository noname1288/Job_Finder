package com.example.jobfinder.domain.usecase

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.domain.repository.CandidateRepository

class UpdateApplicationStatusUseCase(private val repository: CandidateRepository) {
    suspend operator fun invoke(applicationId: Int, status: String) : NetworkResult<BaseResponse<Unit>> {
        return when(val res = repository.updateApplicationStatus(applicationId, status)){
            is NetworkResult.Error -> res
            is NetworkResult.Success-> {
                val result = res.data

                if (result.code == 200){
                    NetworkResult.Success(result)
                }else {
                    NetworkResult.Error(result.message ?: "Error when update application status")
                }
            }
        }
    }
}