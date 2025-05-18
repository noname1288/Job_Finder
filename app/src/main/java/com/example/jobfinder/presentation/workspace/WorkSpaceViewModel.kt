package com.example.jobfinder.presentation.workspace

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.local.UserSessionManager
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.domain.usecase.GetAllJobsByRecruiterIdUseCase
import com.example.jobfinder.presentation.BaseUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkspaceViewModel(private val getAllJobsUseCase: GetAllJobsByRecruiterIdUseCase) : ViewModel() {
    private val _stateWorkspace = MutableStateFlow(WorkSpaceState())
    val stateWorkspace: StateFlow<WorkSpaceState> = _stateWorkspace

    fun getAllJobs(){
        viewModelScope.launch {
            _stateWorkspace.value = _stateWorkspace.value.copy(isLoading = true)
            //fetch data
            val request = UserSessionManager.getUserId()
            val result = getAllJobsUseCase(request)
            Log.d("WorkSpaceViewModel", "getAllJobs: $result")

            when(result){
                is NetworkResult.Error ->{
                    _stateWorkspace.value = _stateWorkspace.value.copy(isLoading = false, errorMessage = result.message)
                }
                is NetworkResult.Success->{
                    Log.d("WorkSpaceViewModel", "getAllJobs: ${result.data}")

                    _stateWorkspace.value = _stateWorkspace.value.copy(
                        isLoading = false,
                        isSuccess = true,
                        allJobs = result.data
                    )
                }
            }

        }
    }

}

data class WorkSpaceState(
    val allJobs : List<Job> = emptyList(),
    override val errorMessage: String? = null,
    override val isLoading: Boolean = false,
    override val isSuccess: Boolean = false
): BaseUIState()