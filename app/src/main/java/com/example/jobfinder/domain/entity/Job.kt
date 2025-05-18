package com.example.jobfinder.domain.entity

import java.time.LocalDateTime

data class Job(
    val id: Int,

    val title: String,
    val description: String,
    val requirement: String,
    val benefit: String,
    val salary: String,
    val location: String,

    val numberOfPositions: Int,
    val candidateCount:Int,

    val createAt: LocalDateTime?,
    val updateAt: LocalDateTime?,
    val endAt: LocalDateTime?,

    val shift: Shift,


    val recruiter: String,
    val status: String,
)

data class Shift(
    val endTime: LocalDateTime?,
    val name: String,
    val startTime: LocalDateTime?
)

fun Job.calculatePregress() : Float{
    if (numberOfPositions == 0) return 0f
    return candidateCount.toFloat() / numberOfPositions.toFloat()
}
