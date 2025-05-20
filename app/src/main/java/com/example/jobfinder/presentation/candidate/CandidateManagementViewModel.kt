package com.example.jobfinder.presentation.candidate

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.local.UserSessionManager
import com.example.jobfinder.data.remote.dto.response.JobDTO3
import com.example.jobfinder.domain.repository.CandidateRepository
import com.example.jobfinder.presentation.BaseUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CandidateManagementViewModel(private val repository: CandidateRepository) : ViewModel() {
    private val _state = MutableStateFlow(CandidateManagementState())
    val state: StateFlow<CandidateManagementState> = _state

    //handle event
    fun onEvent(event: CandidateManagementEvent) {
        when (event) {
            CandidateManagementEvent.Refresh -> {
                fetchCandidateManagement()
            }
        }
    }

    private fun fetchCandidateManagement() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val recruiterId = UserSessionManager.getUserId()
            val date = "2025-05"

            val response = repository.getInfoForCandidateManagementPage(recruiterId, date)

            when(response){
                is NetworkResult.Error ->{
                    _state.value = _state.value.copy(isLoading = false, errorMessage = response.message)
                }
                is NetworkResult.Success ->{
                    val result = response.data

                    if (result.code == 200){
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isSuccess = true,
                            candidateNumber = result.data!!.candidateNumber,
                            jobNumber = result.data.jobNumber,
                            jobs = result.data.jobs
                        )
                        Log.d("CandidateManagementViewModel", "fetchCandidateManagement: ${result.data}")
                    }
                    else {
                        _state.value = _state.value.copy(isLoading = false, errorMessage = result.message)
                    }
                }
            }
        }
    }

}

data class CandidateManagementState(
    val candidateNumber: Int = 0,
    val jobNumber: Int = 0,
    val jobs: List<JobDTO3> = emptyList(),

    override val errorMessage: String? = null,
    override val isLoading: Boolean = false,
    override val isSuccess: Boolean = false,
) : BaseUIState()

sealed class CandidateManagementEvent {
    object Refresh : CandidateManagementEvent()
}