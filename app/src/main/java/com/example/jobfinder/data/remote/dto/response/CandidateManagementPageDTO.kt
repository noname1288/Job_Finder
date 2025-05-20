package com.example.jobfinder.data.remote.dto.response

data class CandidateManagementPageDTO(
    val candidateNumber: Int,
    val jobNumber: Int,
    val jobs : List<JobDTO3>
)