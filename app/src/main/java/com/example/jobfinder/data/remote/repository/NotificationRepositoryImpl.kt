package com.example.jobfinder.data.remote.repository

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.core.safeApiCall
import com.example.jobfinder.data.remote.api.NotificationAPI
import com.example.jobfinder.data.remote.dto.response.NotificationResponse
import com.example.jobfinder.domain.repository.NotificationRepository

class NotificationRepositoryImpl(private val notiApi: NotificationAPI) : NotificationRepository {
    override suspend fun getAllNotifications(userId: Int): NetworkResult<List<NotificationResponse>> {
        return safeApiCall {
            notiApi.getAllNotifications(userId)
        }
    }
}