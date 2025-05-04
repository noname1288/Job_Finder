package com.example.jobfinder.service_locator

import com.example.jobfinder.data.remote.RetrofitHelper
import com.example.jobfinder.data.remote.repository.UserRepositoryImpl
import com.example.jobfinder.domain.repository.UserRepository
import com.example.jobfinder.domain.usecase.LoginUseCase


/**
 * Đóng vai trò DI tối giản (thay thế Hilt/Koin).
 * Giữ SINGLETON cho toàn app.
 */
object AppContainer {

    /* ---------- Data layer ---------- */
    private val userApi            = RetrofitHelper.userAPI          // Retrofit service
    private val userRepository: UserRepository = UserRepositoryImpl(userApi)

    /* ---------- Domain layer ---------- */
    val loginUseCase               = LoginUseCase(userRepository)
}