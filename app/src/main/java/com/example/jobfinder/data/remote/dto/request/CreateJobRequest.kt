package com.example.jobfinder.data.remote.dto.request

import com.example.jobfinder.domain.entity.Shift

data class CreateJobRequest(
    val title: String,
    val description: String,
    val requirement: String,
    val salary: String,
    val benefit: String,
    val location: String,
    val numberOfPositions: Int,
    val deadLine: String,
    val shift: ShiftDTO,
)

data class ShiftDTO(
    val endTime: String,
    val name: String,
    val startTime: String
)