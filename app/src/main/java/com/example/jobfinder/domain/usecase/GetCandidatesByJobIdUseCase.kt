package com.example.jobfinder.domain.usecase

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.response.ApplicationResponse
import com.example.jobfinder.domain.repository.CandidateRepository

class GetCandidatesByJobIdUseCase (private val repository: CandidateRepository) {
    suspend operator fun invoke(jobId: Int): NetworkResult<List<ApplicationResponse>> {
        return when (val res = repository.getApplicationsByJobId(jobId)) {
            is NetworkResult.Error -> res
            is NetworkResult.Success->{
                val result = res.data

                if (result.code == 200 && result.data != null){
                    return NetworkResult.Success(result.data)
                } else {
                    return NetworkResult.Error(result.message ?: "Not found candidates")
                }
            }
        }
    }
}