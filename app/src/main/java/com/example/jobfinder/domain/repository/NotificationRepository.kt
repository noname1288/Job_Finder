package com.example.jobfinder.domain.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.response.NotificationResponse

interface NotificationRepository {
    //Lấy danh sách thông báo
    suspend fun getAllNotifications(userId: Int): NetworkResult<List<NotificationResponse>>

}