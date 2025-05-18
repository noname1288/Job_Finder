package com.example.jobfinder.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.local.UserSessionManager
import com.example.jobfinder.data.remote.mapper.toJob
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.presentation.BaseUIState
import com.example.jobfinder.service_locator.AppContainer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel(){

    private val _stateHome = MutableStateFlow(HomeState())
    val stateHome: StateFlow<HomeState> = _stateHome

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _stateHome.value = _stateHome.value.copy(isLoading = true)

            //fetch data
            val request_recruiter : Int = UserSessionManager.getUserId()
            val request_month : Int = 5 //todo handle: get month filter

            val result = AppContainer.jobRepository.getJobsInHomePage(request_recruiter, request_month)

            when(result){
                is NetworkResult.Error -> {_stateHome.value = _stateHome.value.copy(isLoading = false, errorMessage = result.message)}

                is NetworkResult.Success -> {
                    _stateHome.value = stateHome.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        workingJob = result.data.WORKING,
                        openJob = result.data.OPEN,
                        closeJob = result.data.CLOSE,
                        listJobs = result.data.job.map { it.toJob() }
                    )
                }
            }



        }
    }


}

data class HomeState(
    val workingJob : Int = 0,
    val openJob: Int = 0,
    val closeJob: Int = 0,
    val listJobs : List<Job> = emptyList(),
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    override val isSuccess: Boolean = false
) : BaseUIState()