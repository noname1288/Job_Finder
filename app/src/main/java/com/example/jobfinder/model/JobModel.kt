package com.example.jobfinder.model

data class Job(
    val jobID: String,
    var title:String,
    var desciption:String,
    var requirement:String,
    var benefit:String,
    var location:String,
    var salary:Float,
    var shift:Shift,
    var createAt:String,
    var deadline:String,
    var duration: String,
    var recuiter:String,
    var quantity:Int,
)

data class Shift(
    val shiftID: String,
    var name: String?,
    var startTime: String,
    var endTime: String,
)
