package com.example.jobfinder.presentation.candidate.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.remote.dto.response.ApplicationResponse
import com.example.jobfinder.domain.usecase.GetCandidatesByJobIdUseCase
import com.example.jobfinder.domain.usecase.UpdateApplicationStatusUseCase
import com.example.jobfinder.presentation.BaseUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CandidateListViewModel(
    private val getCandidatesByJobIdUseCase: GetCandidatesByJobIdUseCase,
    private val updateApplicationStatusUseCase: UpdateApplicationStatusUseCase
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
            is CandidateListEvent.UpdateApplicationStatus ->{
                updateApplicationStatus(event.appId, event.statusApp)
            }

//            is CandidateListEvent.AppIdChanged ->{
//                _state.value = _state.value.copy(applicationSelected = event.appId)
//            }
//            is CandidateListEvent.AppStatusChanged ->{
//                _state.value = _state.value.copy(statusApplication = event.statusApp)
//            }
        }
    }

    private fun updateApplicationStatus( appId: Int,  statusApp: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val response = updateApplicationStatusUseCase(
                appId,
                statusApp
            )

            when (response){
                is NetworkResult.Error -> {
                    _state.value = _state.value.copy(isLoading = false, errorMessage = response.message)
                    Log.d("CandidateListViewModel", "updateApplicationStatus: ${response.message}")
                }
                is NetworkResult.Success->{
                    Log.d("CandidateListViewModel", "updateApplicationStatus: ${response.data.message}")
                    _state.value = _state.value.copy(isLoading = false, isUpdated = true)

                    // Sau khi cập nhật trạng thái, lấy lại danh sách ứng viên
                    fetchCandidates() // Gọi lại hàm fetchCandidates để lấy danh sách mới
                }
            }

        }
    }

    fun resetState() {
        _state.value = _state.value.copy(
            isUpdated = false,
            isLoading = false,
            isSuccess = false,
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
    val isUpdated: Boolean = false,

    val applicationList: List<ApplicationResponse> = emptyList(),

    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    override val isSuccess: Boolean = false
) : BaseUIState()

sealed class CandidateListEvent {
    //    object Refresh : CandidateListEvent()
//    data class AppIdChanged(val appId: String) : CandidateListEvent()
//    data class AppStatusChanged(val statusApp: String) : CandidateListEvent()
    data class UpdateApplicationStatus(
        val appId: Int ,
        val statusApp: String
    ) : CandidateListEvent()
    data class JobIdChanged(val jobId: String) : CandidateListEvent()
    object GetCandidates : CandidateListEvent()
    object ResetState : CandidateListEvent()
}