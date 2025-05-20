package com.example.jobfinder.data.remote.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.core.safeApiCall
import com.example.jobfinder.data.remote.api.CandidateAPI
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.response.ApplicationResponse
import com.example.jobfinder.data.remote.dto.response.CandidateManagementPageDTO
import com.example.jobfinder.domain.repository.CandidateRepository

class CandidateRepositoryImpl (private val candidateApi: CandidateAPI) : CandidateRepository  {
    override suspend fun getApplicationsByJobId(jobId: Int): NetworkResult<BaseResponse<List<ApplicationResponse>>> {
        return safeApiCall {
            candidateApi.getApplicationByJobId(jobId)
        }
    }

    override suspend fun getInfoForCandidateManagementPage(
        recruiterId: Int,
        date: String
    ): NetworkResult<BaseResponse<CandidateManagementPageDTO>> {
        return safeApiCall { candidateApi.getCandidateInPage(recruiterId, date) }
    }
}