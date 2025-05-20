package com.example.jobfinder.domain.usecase

import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.RegisterUserRequest
import com.example.jobfinder.domain.repository.UserRepository

class RegisterUseCase (private val userRepository: UserRepository){
    suspend operator fun invoke(request: RegisterUserRequest) : NetworkResult<BaseResponse<Unit>>{
        return when(val result = userRepository.register(request)){
            is NetworkResult.Error -> result
            is NetworkResult.Success -> {
                val res = result.data

                if (res.code == 201 && res.data == null){
                    NetworkResult.Success(res)
                } else NetworkResult.Error(res.message ?: "Register failed")
            }
        }
    }
}