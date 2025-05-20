package com.example.jobfinder.service_locator

import com.example.jobfinder.data.remote.RetrofitHelper
import com.example.jobfinder.data.remote.repository.CandidateRepositoryImpl
import com.example.jobfinder.data.remote.repository.JobRepositoryImpl
import com.example.jobfinder.data.remote.repository.NotificationRepositoryImpl
import com.example.jobfinder.data.remote.repository.UserRepositoryImpl
import com.example.jobfinder.domain.repository.CandidateRepository
import com.example.jobfinder.domain.repository.JobRepository
import com.example.jobfinder.domain.repository.NotificationRepository
import com.example.jobfinder.domain.repository.UserRepository
import com.example.jobfinder.domain.usecase.CreateJobUseCase
import com.example.jobfinder.domain.usecase.DeleteJobByIdUseCase
import com.example.jobfinder.domain.usecase.GetAllJobsByRecruiterIdUseCase
import com.example.jobfinder.domain.usecase.GetCandidatesByJobIdUseCase
import com.example.jobfinder.domain.usecase.GetDetailJobByIdUseCase
import com.example.jobfinder.domain.usecase.GetSeekerProfileUseCase
import com.example.jobfinder.domain.usecase.LoginUseCase
import com.example.jobfinder.domain.usecase.RegisterUseCase
import com.example.jobfinder.domain.usecase.UpdateApplicationStatusUseCase


/**
 * Đóng vai trò DI tối giản (thay thế Hilt/Koin).
 * Giữ SINGLETON cho toàn app.
 */
object AppContainer {
    /* ---------- Data layer ---------- */
    private val userApi = RetrofitHelper.userAPI          // Retrofit service
    private val postApi = RetrofitHelper.postAPI
    private val candidateApi = RetrofitHelper.candidateAPI
    private val notificationApi = RetrofitHelper.notificationAPI

    private val userRepository: UserRepository by lazy {
        UserRepositoryImpl(userApi)
    }

    val jobRepository: JobRepository by lazy {
        JobRepositoryImpl(postApi)
    }

    val candidateRepository: CandidateRepository by lazy {
        CandidateRepositoryImpl(candidateApi)
    }

    val notificationRepository: NotificationRepository by lazy {
        NotificationRepositoryImpl(notificationApi)
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
    val getCandidatesByJobIdUseCase by lazy {
        GetCandidatesByJobIdUseCase(candidateRepository)
    }
    val updateApplicationStatusUseCase by lazy {
        UpdateApplicationStatusUseCase(candidateRepository)
    }
    val getSeekerProfileUseCase by lazy {
        GetSeekerProfileUseCase(userRepository)
    }
}