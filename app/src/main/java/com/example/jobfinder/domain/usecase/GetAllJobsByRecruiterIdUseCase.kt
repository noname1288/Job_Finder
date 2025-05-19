package com.example.jobfinder.domain.usecase

import android.util.Log
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.mapper.toJob
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.domain.repository.JobRepository

class GetAllJobsByRecruiterIdUseCase(private val repository: JobRepository) {
    suspend operator fun invoke(recruiterId: Int): NetworkResult<List<Job>> {
         return when (val result = repository.getAllJobs(recruiterId)) {
            is NetworkResult.Error -> result
            is NetworkResult.Success -> {
                val res = result.data

                if(res.code == 200 && res.data != null){
                    Log.d("GetAllJobsByRecruiterIdUseCase", "invoke: ${res.data}")

                    NetworkResult.Success(res.data.map { it.toJob()})
                }else {
                    NetworkResult.Error(res.message ?: "Getting all jobs failed")
                }
            }
        }
    }

}