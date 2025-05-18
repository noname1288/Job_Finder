package com.example.jobfinder.data.remote

import com.example.jobfinder.data.remote.api.JobAPI
import com.example.jobfinder.data.remote.api.UserAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val BASE_URL = "http://192.168.0.15:8080"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //api
    //login, register
    val userAPI: UserAPI by lazy { retrofit.create(UserAPI::class.java) }
    val postAPI: JobAPI by lazy { retrofit.create(JobAPI::class.java) }
}