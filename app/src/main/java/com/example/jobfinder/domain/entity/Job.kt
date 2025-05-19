package com.example.jobfinder.domain.entity

import java.time.LocalDateTime

data class Job(
    val id: Int=0,

    val title: String="",
    val description: String="",
    val requirement: String="",
    val benefit: String="",
    val salary: String="",
    val location: String="",

    val numberOfPositions: Int=0,
    val candidateCount:Int=0,

    var createAt: LocalDateTime? = LocalDateTime.now(), // bắt đắt tuyển
    var updateAt: LocalDateTime?=null, // update thời gian ứng tuyển
    var endAt: LocalDateTime?= LocalDateTime.now(), // hết hạn đăng tuyển

    val shift: Shift = Shift(),


    val recruiter: String="",
    val status: String="",
)

data class Shift(
    var endTime: LocalDateTime?= LocalDateTime.now(),
    var name: String="",
    var startTime: LocalDateTime?= LocalDateTime.now()
)

fun Job.calculatePregress() : Float{
    if (numberOfPositions == 0) return 0f
    return candidateCount.toFloat() / numberOfPositions.toFloat()
}
