package com.example.jobfinder.domain.usecase

import com.example.jobfinder.domain.entity.Recruiter1
import com.example.jobfinder.domain.repository.UserRepository

class RegisterUseCase (private val userRepository: UserRepository){
    suspend operator fun invoke(fullName: String, email: String, password: String, role: String){
        val recruiter = Recruiter1(
            id = 0,
            fulName = fullName,
            email = email,
            password = password,
            picUri = null,
            token = null,
            companyName = null,
            companyDescription =null,
            companyAddress = null,
            companyPhone = null,
            role_recrutier = role
        )
        userRepository.register(recruiter)
    }
}