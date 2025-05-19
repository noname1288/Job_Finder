package com.example.jobfinder.presentation.workspace.create

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.local.UserSessionManager
import com.example.jobfinder.data.remote.dto.request.CreateJobRequest
import com.example.jobfinder.data.remote.dto.request.ShiftDTO
import com.example.jobfinder.domain.entity.Shift
import com.example.jobfinder.domain.usecase.CreateJobUseCase
import com.example.jobfinder.presentation.BaseUIState
import com.example.jobfinder.utils.Utils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CreateJobViewModel(
    private val createJobUseCase: CreateJobUseCase,
) : ViewModel(){
    private val _stateCreateJob = MutableStateFlow(CreatJobUIState())
    val stateCreateJob: StateFlow<CreatJobUIState> = _stateCreateJob

    //handle event
    fun onCreateEvent(event: CreateJobEvent){
        when(event) {
            is CreateJobEvent.BenefitChanged -> {
                _stateCreateJob.value = _stateCreateJob.value.copy(benefit = event.benefit)
            }
            is CreateJobEvent.DescriptionChanged -> {
                _stateCreateJob.value = _stateCreateJob.value.copy(description = event.description)
            }
            is CreateJobEvent.EndAtChanged -> {
                _stateCreateJob.value = _stateCreateJob.value.copy(endAt = event.endAt)
            }
            is CreateJobEvent.EndTimeChanged -> {
                _stateCreateJob.value = _stateCreateJob.value.copy(endTime = event.endTime)
            }
            is CreateJobEvent.LocationChanged -> {
                _stateCreateJob.value = _stateCreateJob.value.copy(location = event.location)
            }
            is CreateJobEvent.NumberOfPositionsChanged -> {
                _stateCreateJob.value = _stateCreateJob.value.copy(numberOfPositions = event.numberOfPositions.toString())
            }
            is CreateJobEvent.RequirementChanged -> {
                _stateCreateJob.value = _stateCreateJob.value.copy(requirement = event.requirement)
            }
            is CreateJobEvent.SalaryChanged -> {
                _stateCreateJob.value = _stateCreateJob.value.copy(salary = event.salary)
            }

            is CreateJobEvent.StartTimeChanged -> {
                _stateCreateJob.value = _stateCreateJob.value.copy(startTime = event.startTime)
            }
            is CreateJobEvent.TitleChanged -> {
                _stateCreateJob.value = _stateCreateJob.value.copy(title = event.title)
            }
            is CreateJobEvent.WorkingAtChanged -> {
                _stateCreateJob.value = _stateCreateJob.value.copy(workingAt = event.workingAt)
            }

            CreateJobEvent.SendRequest -> {
                createJob()
            }

            CreateJobEvent.ResetState -> {
                clearDataAndState()
            }
        }
    }

    private fun createJob() {
        viewModelScope.launch {
            _stateCreateJob.value = _stateCreateJob.value.copy(isLoading = true)

            val request = CreateJobRequest(
                title =_stateCreateJob.value.title,
                description = _stateCreateJob.value.description,
                requirement =_stateCreateJob.value.requirement,
                salary = _stateCreateJob.value.salary,
                benefit = _stateCreateJob.value.benefit,
                location = _stateCreateJob.value.location,
                numberOfPositions = _stateCreateJob.value.numberOfPositions.toInt(),
                deadLine = _stateCreateJob.value.endAt, // hạn đăng tuyển
                shift = ShiftDTO(
                    name = "null",
                    startTime = _stateCreateJob.value.startTime,
                    endTime = _stateCreateJob.value.endTime,
                )

            )

//            val request1 = CreateJobRequest(
//                title ="T1",
//                description = "des1",
//                requirement = "req1",
//                salary = "1",
//                benefit = "1",
//                location = "1",
//                numberOfPositions = 4,
//                deadLine = "2025-05-20T23:59:59",
//                shift = ShiftDTO(
//                    name = "nonn",
//                    startTime = "2025-05-21T09:00:00",
//                    endTime = "2025-05-21T18:00:00",
//                )
//            )

            Log.d("CreateJobViewModel", request.toString())
//            Log.d("CreateJobViewModel", request1.toString())


            val response = createJobUseCase(
                recruiterId = UserSessionManager.getUserId(),
                createJobRequest = request
            )


            when(response){
                is NetworkResult.Error ->{
                    _stateCreateJob.value = _stateCreateJob.value.copy(
                        errorMessage = response.message,
                        isLoading = false
                    )
                }
                is NetworkResult.Success->{
                    _stateCreateJob.value = _stateCreateJob.value.copy(
                        isSuccess = true,
                        isLoading = false
                    )
                    Log.d("CreateJobViewModel", response.data.toString())

                }
            }


        }
    }

    fun clearDataAndState(){
        _stateCreateJob.value = _stateCreateJob.value.copy(isSuccess = false, errorMessage = null, isLoading = false)
//        _stateCreateJob.value = CreatJobUIState()
    }
}

data class CreatJobUIState(
    val title:String = "",
    val description:String = "",
    val requirement:String = "",
    val benefit:String = "",
    val salary:String = "",
    val location: String="",

    val numberOfPositions: String="1", //convert into Int before send to server

    val workingAt : String = "", // chọn ngày làm việc
    val endAt :String = "", //hạn đăng tuyển

    val nameShift: String="",
    val startTime: String="09:00",
    val endTime: String="11:00",

    override val errorMessage: String? = null,
    override val isLoading: Boolean = false,
    override val isSuccess: Boolean = false
): BaseUIState()

sealed class CreateJobEvent{
    data class TitleChanged(val title:String): CreateJobEvent()
    data class DescriptionChanged(val description:String): CreateJobEvent()
    data class RequirementChanged(val requirement:String): CreateJobEvent()
    data class BenefitChanged(val benefit:String): CreateJobEvent()
    data class SalaryChanged(val salary: String): CreateJobEvent()
    data class LocationChanged(val location:String): CreateJobEvent()
    data class NumberOfPositionsChanged(val numberOfPositions: String): CreateJobEvent()
    data class EndAtChanged(val endAt:String): CreateJobEvent()
    data class StartTimeChanged(val startTime:String): CreateJobEvent()
    data class EndTimeChanged(val endTime:String): CreateJobEvent()
    data class WorkingAtChanged(val workingAt:String): CreateJobEvent()


    object SendRequest: CreateJobEvent()
    object ResetState : CreateJobEvent()
}