package com.example.jobfinder.data.remote.dto.response

data class NotificationResponse(
    val createdAt: String = "2025-05-20T17:17:37.461505",
    val id: Int,
    val jobPostingId: Int,
    val message: String,
    val status: Boolean,
    val title: String
)