package com.example.jobfinder.data.remote.dto.request

import com.example.jobfinder.domain.entity.Shift

data class CreateJobRequest(
    val title: String,
    val description: String,
    val requirement: String,
    val salary: String,
    val benefit: String,
    val location: String,
    val numberOfPositions: String,
    val deadLine: String,
    val shift: Shift,
)