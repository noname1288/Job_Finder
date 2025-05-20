package com.example.jobfinder.data.remote.dto.response

data class JobTemp(
    val jobId:Int = 0,
    val companyAddress: String="",
    val createAt: String="",
    val endAt: String="",
    val numberOfApplicants: Int=0,
    val numberOfRecruit: Int=0,
    val title: String="",
    val updateAt: String=""
)