package com.example.jobfinder.data.remote.api

import com.example.jobfinder.data.remote.dto.response.NotificationResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NotificationAPI {
    @GET("/api/notifications/user/{userId}")
    suspend fun getAllNotifications(@Path("userId") userId: String): List<NotificationResponse>
}