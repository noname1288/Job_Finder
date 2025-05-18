package com.example.jobfinder.service_locator

import com.example.jobfinder.data.remote.RetrofitHelper
import com.example.jobfinder.data.remote.repository.JobRepositoryImpl
import com.example.jobfinder.data.remote.repository.UserRepositoryImpl
import com.example.jobfinder.domain.repository.JobRepository
import com.example.jobfinder.domain.repository.UserRepository
import com.example.jobfinder.domain.usecase.CreateJobUseCase
import com.example.jobfinder.domain.usecase.LoginUseCase
import com.example.jobfinder.domain.usecase.RegisterUseCase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


/**
 * Đóng vai trò DI tối giản (thay thế Hilt/Koin).
 * Giữ SINGLETON cho toàn app.
 */
object AppContainer {
    /* ---------- function ---------- */
//    Chuyển LocalDateTime → String
    fun localDateTimeToString(dateTime: LocalDateTime?): String {
        if (dateTime == null) return ""
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return dateTime.format(formatter)
    }
//    Chuyển String → LocalDateTime
    fun stringToLocalDateTime(dateTimeString: String?): LocalDateTime? {
        if(dateTimeString.isNullOrBlank()) return null
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        return LocalDateTime.parse(dateTimeString, formatter)
    }


    /* ---------- Data layer ---------- */
    private val userApi = RetrofitHelper.userAPI          // Retrofit service
    private val postApi = RetrofitHelper.postAPI

    private val userRepository: UserRepository by lazy {
        UserRepositoryImpl(userApi)
    }

    val jobRepository: JobRepository by lazy {
        JobRepositoryImpl(postApi)
    }



    /* ---------- Domain layer ---------- */
    val loginUseCase by lazy {
        LoginUseCase(userRepository)
    }
    val registerUseCase by lazy {
        RegisterUseCase(userRepository)
    }
    val createJobUseCase by lazy {
        CreateJobUseCase(jobRepository)
    }
}