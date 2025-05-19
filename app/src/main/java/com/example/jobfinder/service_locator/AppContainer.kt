package com.example.jobfinder.service_locator

import com.example.jobfinder.data.remote.RetrofitHelper
import com.example.jobfinder.data.remote.repository.JobRepositoryImpl
import com.example.jobfinder.data.remote.repository.UserRepositoryImpl
import com.example.jobfinder.domain.repository.JobRepository
import com.example.jobfinder.domain.repository.UserRepository
import com.example.jobfinder.domain.usecase.CreateJobUseCase
import com.example.jobfinder.domain.usecase.DeleteJobByIdUseCase
import com.example.jobfinder.domain.usecase.GetAllJobsByRecruiterIdUseCase
import com.example.jobfinder.domain.usecase.GetDetailJobByIdUseCase
import com.example.jobfinder.domain.usecase.LoginUseCase
import com.example.jobfinder.domain.usecase.RegisterUseCase


/**
 * Đóng vai trò DI tối giản (thay thế Hilt/Koin).
 * Giữ SINGLETON cho toàn app.
 */
object AppContainer {
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
    val getAllJobsUseCase by lazy {
        GetAllJobsByRecruiterIdUseCase(jobRepository)
    }
    val getDetailJobByIdUseCase by lazy {
        GetDetailJobByIdUseCase(jobRepository)
    }
    val deleteJobByIdUseCase by lazy {
        DeleteJobByIdUseCase(jobRepository)
    }
}