package com.example.jobfinder.data.remote.dto.response

data class NotificationResponse(
    val createdAt: String,
    val id: Int,
    val jobPostingId: Int,
    val message: String,
    val status: Boolean,
    val title: String
)