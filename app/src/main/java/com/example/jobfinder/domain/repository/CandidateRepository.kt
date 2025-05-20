package com.example.jobfinder.domain.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.response.ApplicationResponse


interface CandidateRepository {
    suspend fun getApplicationsByJobId(jobId: Int): NetworkResult<BaseResponse<List<ApplicationResponse>>>

}