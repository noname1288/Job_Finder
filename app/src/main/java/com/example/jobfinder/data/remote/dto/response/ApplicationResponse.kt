package com.example.jobfinder.data.remote.dto.response

data class ApplicationResponse(
    val applicationDate: String,
    val idApplication: Int,
    val idJobPosting: Int,
    val jobSeeker: JobSeekerDTO,
    val status: String
)