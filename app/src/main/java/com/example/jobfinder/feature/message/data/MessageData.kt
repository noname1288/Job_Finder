package com.example.jobfinder.feature.message.data

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