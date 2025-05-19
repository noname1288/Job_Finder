package com.example.jobfinder.data.remote.dto.response

data class CreateJobResponse(
    val benefit: String,
    val deadLine: String,
    val description: String,
    val id: Int,
    val location: String,
    val numberOfPositions: Int,
    val postDate: String,
    val recruiter: String,
    val requirement: String,
    val salary: String,
    val shift: String,
    val status: String,
    val title: String,
    val updatedDate: String
)