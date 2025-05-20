package com.example.jobfinder.presentation.candidate.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.response.JobSeekerDTO2
import com.example.jobfinder.domain.usecase.GetSeekerProfileUseCase
import com.example.jobfinder.presentation.BaseUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CandidateProfileViewModel(
    private val getSeekerProfileUseCase: GetSeekerProfileUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CandidateProfileState())
    val state: StateFlow<CandidateProfileState> = _state

    init {
        loadProfile()
    }

    fun handleEvent(event: CandidateProfileEvent) {
        when (event) {
            is CandidateProfileEvent.SeekerIdChanged -> {
                _state.value = _state.value.copy(seekerId = event.seekerId)
            }
            is CandidateProfileEvent.LoadProfile -> loadProfile()
            is CandidateProfileEvent.ResetState -> _state.value = CandidateProfileState()
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val request : Int = 2
            val result = getSeekerProfileUseCase(2)

            when(result){
                is NetworkResult.Error -> {
                    _state.value = _state.value.copy(isLoading = false, errorMessage = result.message)
                }
                is NetworkResult.Success-> {
                    _state.value = _state.value.copy(isLoading = false, isSuccess = true, seekerProfile = result.data)
                }
            }

            Log.d("CandidateProfileViewModel", "loadProfile: $result")
        }
    }

}

data class CandidateProfileState(
    val seekerProfile: JobSeekerDTO2 = JobSeekerDTO2(),
    val seekerId: String = "1",
    override val errorMessage: String? = null,
    override val isLoading: Boolean = false,
    override val isSuccess: Boolean = false
) : BaseUIState()

sealed class CandidateProfileEvent {
    data class SeekerIdChanged(val seekerId: String) : CandidateProfileEvent()
    object LoadProfile : CandidateProfileEvent()
    object ResetState : CandidateProfileEvent()
}