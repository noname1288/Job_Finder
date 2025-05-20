package com.example.jobfinder.data.remote.dto.response

//using in CandidateManagementPage
data class JobDTO3(
    val jobId: Int,
    val companyAddress: String,
    val createAt: String,
    val endAt: String,
    val numberOfApplicants: Int,
    val numberOfRecruit: Int,
    val title: String,
    val updateAt: String
)