package com.example.jobfinder.core


import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}

/*try-catch dung lai cho moi call api */
suspend fun <T> safeApiCall(
    call: suspend () -> Response<T>,
) : NetworkResult<T> =
    try {
        val res = call()
        // neu call api thanh cong -> tra ve du lieu
        if (res.isSuccessful && res.body()!=null){
            NetworkResult.Success(res.body()!!)
        }else{
            NetworkResult.Error("HTTP ${res.code()} – ${res.message()}")
        }
    }catch (e: IOException)   { NetworkResult.Error("Network error: ${e.message}") } //catch : loss internet, timeout, socket closed,... -> server khong phan hoi
    catch (e: HttpException) { NetworkResult.Error("HTTP exc: ${e.message}") } // catch: error Http response
    catch (e: Exception)     { NetworkResult.Error("Unknown: ${e.message}") }