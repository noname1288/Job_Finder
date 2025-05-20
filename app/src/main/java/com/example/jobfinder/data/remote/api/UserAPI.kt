package com.example.jobfinder.data.remote.api

import com.example.jobfinder.data.remote.dto.request.LoginUserRequest
import com.example.jobfinder.data.remote.dto.BaseResponse
import com.example.jobfinder.data.remote.dto.request.RegisterUserRequest
import com.example.jobfinder.data.remote.dto.response.JobSeekerDTO
import com.example.jobfinder.data.remote.dto.response.JobSeekerDTO2
import com.example.jobfinder.data.remote.dto.response.LoginUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserAPI {
    //LOGIN
    @POST("/auth/login")
    suspend fun login(
       @Body request: LoginUserRequest
    ): Response<BaseResponse<LoginUserResponse>>

    //Resgiter
    @POST("/auth/register")
    suspend fun registerUser(
        @Body request: RegisterUserRequest
    ) : Response<BaseResponse<Unit>>

    //Get profile
    @GET("/auth/profile/{seeker_id}")
    suspend fun getProfile(
        @Path("seeker_id") seekerId : Int
    ) : Response<BaseResponse<JobSeekerDTO2>>

    /*
    *với json kiểu
    * {
          "code": 201,
          "message": "User registered successfully",
          "data": null
        }
    * */
    // nên chọn Response<BaseResponse<Unit>> hay Response<BaseResponse<Nothing>>
    /*
     * BaseResponse<Unit> nghĩa là data là null hoặc Unit,
     * trong trường hợp data là null thì bạn không cần xử lý thêm gì.
     * Nothing trong Kotlin không có giá trị nào,
     * dùng sai sẽ khiến việc deserialize từ JSON trả về data: null bị lỗi hoặc gây bất tiện.
    → ✅ Vì vậy: dùng BaseResponse<Unit> là best practice khi data không cần quan tâm.
    * */
}