package com.example.jobfinder.domain.entity

/**
 * Mỗi tin nhắn trong 1 phòng chat
 */
data class MessageData(
    val sender: String,
    val content: String,
    val time: String,
    val isMine: Boolean,
    val imageUrl: String? = null // optional
)