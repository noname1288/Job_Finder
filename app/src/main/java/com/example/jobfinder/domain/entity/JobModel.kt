package com.example.jobfinder.domain.entity

data class Job(
    val jobID: String,
    var title: String, // tên công việc
    var desciption: String, // mô tả
    var requirement: String, // Yêu cầu
    var benefit: String, //quyeefn lợi
    var location: String,
    var salary: Float,
    var shift: Shift, //ca làm việc
    var createAt: String, //thời điểm tạo (String/ Long)
    var deadline: String,
    var duration: String, // hạn của bài đăng --> để chuyển trạng thái hủy/ bắt đầu thực hiện việc đó == đóng tuyển
    var recuiter: String, // ? liên kết với công ty tuyển dụng hay id nhà tuyển dụng đăng bài đó?
    var quantity: Int, //số lượng tuyển
)

data class Shift(
//ca làm việc
    val shiftID: String,
    var name: String?,
    var startTime: String,
    var endTime: String,
)
