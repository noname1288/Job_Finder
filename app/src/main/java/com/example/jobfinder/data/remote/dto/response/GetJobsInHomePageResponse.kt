package com.example.jobfinder.data.remote.dto.response

data class GetJobsInHomePageResponse(
    val CLOSE: Int,
    val OPEN: Int,
    val WORKING: Int,
    val job: List<JobTemp>
)