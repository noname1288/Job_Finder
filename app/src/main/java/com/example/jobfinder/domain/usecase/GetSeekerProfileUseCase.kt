package com.example.jobfinder.domain.usecase

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.response.JobSeekerDTO
import com.example.jobfinder.data.remote.dto.response.JobSeekerDTO2
import com.example.jobfinder.domain.repository.UserRepository

class GetSeekerProfileUseCase (private val repository: UserRepository){
    suspend operator fun invoke(seekerId: Int) : NetworkResult<JobSeekerDTO2>{
        return when (val res = repository.getProfile(seekerId)){
            is NetworkResult.Error -> res
            is NetworkResult.Success -> {
                val result = res.data

                if (result.code == 200 && result.data != null){
                    NetworkResult.Success(result.data)
                }else {
                    NetworkResult.Error(result.message ?: "Not found seeker's profile")
                }
            }
        }
    }

}