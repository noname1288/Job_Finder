package com.example.jobfinder.presentation.workspace.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.domain.usecase.DeleteJobByIdUseCase
import com.example.jobfinder.domain.usecase.GetDetailJobByIdUseCase
import com.example.jobfinder.presentation.BaseUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class JobDetailViewModel(
    private val getDetailJobByIdUseCase: GetDetailJobByIdUseCase,
    private val deleteJobByIdUseCase: DeleteJobByIdUseCase

) : ViewModel() {
    private val _stateJobDetail = MutableStateFlow(JobDetailState())
    val stateJobDetail: StateFlow<JobDetailState> = _stateJobDetail

    //handle Event
    fun onEvent(event: JobDetailEvent) {
        when (event) {
            is JobDetailEvent.JobIdChanged -> {
                _stateJobDetail.value = _stateJobDetail.value.copy(jobId = event.jobId)
            }

            JobDetailEvent.GetJobDetailById -> {
                getJobDetailById()
            }

//            JobDetailEvent.UpdateJobStatus -> TODO()
            JobDetailEvent.DeleteJob -> {
                deleteJobById()
            }

            JobDetailEvent.ResetState -> {
                resetState()
            }
        }
    }

    private fun deleteJobById() {
        viewModelScope.launch {
            _stateJobDetail.value = _stateJobDetail.value.copy(isLoading = true)

            //send request to delete
            val request = _stateJobDetail.value.jobId.toInt()
            val response = deleteJobByIdUseCase(request)

            when (response) {
                is NetworkResult.Success -> {
                    _stateJobDetail.value = _stateJobDetail.value.copy(
                        isSuccess = true,
                        isLoading = false,
                        isDeleted = true, // chỉ set khi xoá
                        errorMessage = null
                    )
                }

                is NetworkResult.Error -> {
                    _stateJobDetail.value = _stateJobDetail.value.copy(
                        isSuccess = false,
                        isLoading = false,
                        errorMessage = response.message
                    )
                }
            }
        }
    }

    private fun getJobDetailById() {
        viewModelScope.launch {
            _stateJobDetail.value = _stateJobDetail.value.copy(isLoading = true)

            //fetch data
            val request = _stateJobDetail.value.jobId.toInt()
            val response = getDetailJobByIdUseCase(request)

            when (response) {
                is NetworkResult.Error -> {
                    _stateJobDetail.value = _stateJobDetail.value.copy(
                        errorMessage = response.message,
                        isLoading = false
                    )
                }

                is NetworkResult.Success -> {
                    _stateJobDetail.value = _stateJobDetail.value.copy(
                        detailJob = response.data,
                        isLoading = false,
                        isSuccess = true
                    )
                }
            }
        }
    }

    fun resetState() {
        _stateJobDetail.value = _stateJobDetail.value.copy(
            isSuccess = false,
            isDeleted = false,
            isLoading = false,
            errorMessage = null
        )
    }

}

data class JobDetailState(
    var jobId: String = "1",
    val detailJob: Job = Job(),
    val isDeleted: Boolean = false, // thêm biến này
    override val errorMessage: String? = null,
    override val isLoading: Boolean = false,
    override val isSuccess: Boolean = false
) : BaseUIState()

sealed class JobDetailEvent {
    data class JobIdChanged(val jobId: String) : JobDetailEvent()
    object GetJobDetailById : JobDetailEvent()

    //    object UpdateJobStatus : JobDetailEvent()
    object DeleteJob : JobDetailEvent()
    object ResetState : JobDetailEvent()

}