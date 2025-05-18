package com.example.jobfinder.data.remote.dto.response

data class JobTemp2(
    val benefit: String="",
    val deadLine: String="",
    val description: String="",
    val id: Int = 0,
    val location: String="",
    val numberOfPositions: Int = 0,
    val postDate: String="",
    val recruiter: String="",
    val requirement: String="",
    val salary: String="",
    val shift: String="",
    val status: String="",
    val title: String="",
    val updatedDate: String?=null
)