package com.example.jobfinder.domain.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.response.ApplicationResponse
import com.example.jobfinder.data.remote.dto.response.CandidateManagementPageDTO


interface CandidateRepository {
    //lấy danh sách ứng viên chờ duyệt theo jobId
    suspend fun getApplicationsByJobId(jobId: Int): NetworkResult<BaseResponse<List<ApplicationResponse>>>

    //lấy info cho page: CandidateManagement
    suspend fun getInfoForCandidateManagementPage(recruiterId: Int, date: String) : NetworkResult<BaseResponse<CandidateManagementPageDTO>>

    //đồng ý / từ chối đơn ứng tuyển
    suspend fun updateApplicationStatus(applicationId: Int, status: String) : NetworkResult<BaseResponse<Unit>>
}