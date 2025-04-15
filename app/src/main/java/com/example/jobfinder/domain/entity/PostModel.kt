package com.example.jobfinder.domain.entity

data class PostModel(
    val title: String,         // Tiêu đề bài đăng
    val location: String,      // Địa điểm, kho...
    val candidateCount: Int,   // Số ứng viên
    val deadline: String,      // Hạn chót
    val progress: Float        // Tiến độ (0f..1f)
)


object FakeData {
    val mockPosts = listOf(
        PostModel(
            title = "Tìm người vận chuyển hàng kho Hà Nội",
            location = "Kho tổng A",
            candidateCount = 3,
            deadline = "27 tháng 4",
            progress = 0.5f
        ),
        PostModel(
            title = "Tìm người vận chuyển hàng kho Hà Nội",
            location = "Kho tổng A",
            candidateCount = 3,
            deadline = "27 tháng 4",
            progress = 0.2f
        ),
        PostModel(
            title = "Tìm người vận chuyển hàng kho Hà Nội",
            location = "Kho tổng A",
            candidateCount = 3,
            deadline = "27 tháng 4",
            progress = 0.1f
        ),
        PostModel(
            title = "Tìm người vận chuyển hàng kho Hà Nội",
            location = "Kho tổng A",
            candidateCount = 3,
            deadline = "27 tháng 4",
            progress = 0.6f
        ), PostModel(
            title = "Tìm người vận chuyển hàng kho Hà Nội",
            location = "Kho tổng A",
            candidateCount = 3,
            deadline = "27 tháng 4",
            progress = 0.9f
        ),
        PostModel(
            title = "Tìm người vận chuyển hàng kho Hà Nội",
            location = "Kho tổng B",
            candidateCount = 5,
            deadline = "30 tháng 4",
            progress = 0.95f
        )
    )
}