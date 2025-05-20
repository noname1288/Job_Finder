package com.example.jobfinder.data.remote

import com.example.jobfinder.data.remote.api.CandidateAPI
import com.example.jobfinder.data.remote.api.JobAPI
import com.example.jobfinder.data.remote.api.UserAPI
import com.example.jobfinder.data.remote.dto.response.JobSeekerDTO2
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val BASE_URL = "http://192.168.0.15:8080"

    val gson = GsonBuilder()
        .registerTypeAdapter(JobSeekerDTO2::class.java, JobSeekerDTO2Deserializer())
        .create()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    //api
    //login, register
    val userAPI: UserAPI by lazy { retrofit.create(UserAPI::class.java) }

    //job management api: create, get all, get detail, delete, update content/ status,
    val postAPI: JobAPI by lazy { retrofit.create(JobAPI::class.java) }

    //candidate management api: application, approve/reject - candidate
    val candidateAPI : CandidateAPI by lazy { retrofit.create(CandidateAPI::class.java) }

}