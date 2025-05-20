package com.example.jobfinder.presentation.candidate.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.local.UserSessionManager
import com.example.jobfinder.data.remote.dto.response.ApplicationResponse
import com.example.jobfinder.domain.usecase.GetCandidatesByJobIdUseCase
import com.example.jobfinder.presentation.BaseUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CandidateListViewModel(
    private val getCandidatesByJobIdUseCase: GetCandidatesByJobIdUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CandidateListState())
    val state: StateFlow<CandidateListState> = _state

    //handle event
    fun onEvent(event: CandidateListEvent) {
        when (event) {
            is CandidateListEvent.JobIdChanged -> {
                _state.value = _state.value.copy(jobId = event.jobId)
            }

            CandidateListEvent.GetCandidates -> {
                fetchCandidates()
            }
            CandidateListEvent.ResetState -> {resetState()}

        }
    }

    private fun resetState() {
        _state.value = _state.value.copy(
            isRefreshing = false,
            isRejected = false,
            isApproved = false,
            isLoading = false,
            errorMessage = null
        )
    }

    fun fetchCandidates() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val request = _state.value.jobId.toInt()
            val response = getCandidatesByJobIdUseCase(request)

            when (response) {
                is NetworkResult.Error -> {
                    _state.value =
                        _state.value.copy(isLoading = false, errorMessage = response.message)
                }

                is NetworkResult.Success -> {
                    Log.d("CandidateListViewModel", "fetchCandidates: ${response.data}")
                    _state.value = _state.value.copy(isLoading = false, isSuccess = true, applicationList = response.data)
                }
            }
        }


    }


}

data class CandidateListState(
    val jobId:String ="",
    val isRefreshing: Boolean = false,
    val isRejected: Boolean = false,
    val isApproved: Boolean = false,

    val applicationList: List<ApplicationResponse> = emptyList(),

    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    override val isSuccess: Boolean = false
) : BaseUIState()

sealed class CandidateListEvent {
    //    object Refresh : CandidateListEvent()
//    object Approve : CandidateListEvent()
//    object Reject : CandidateListEvent()
    data class JobIdChanged(val jobId: String) : CandidateListEvent()
    object GetCandidates : CandidateListEvent()
    object ResetState : CandidateListEvent()
}