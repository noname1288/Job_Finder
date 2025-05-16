package com.example.jobfinder.domain.entity

data class Job(
    val jobID: String,
    var title: String, // tên công việc
    var desciption: String, // mô tả
    var requirement: String, // Yêu cầu
    var benefit: String, //quyeefn lợi
    var location: String,
    var salary: String,
    var shift: Shift, //ca làm việc
    var createAt: String, //thời điểm tạo bài đăng
    var endAt: String, //thời điểm kết thúc bài đăng tuyển
    var duration: String, // thời gian để thực hiện công việc?
    var recuiter: String, // ? liên kết với công ty tuyển dụng hay id nhà tuyển dụng đăng bài đó?
    var quantity: Int, //số lượng tuyển
)

data class Shift(
    val endTime: String,
    val name: String,
    val startTime: String
)
