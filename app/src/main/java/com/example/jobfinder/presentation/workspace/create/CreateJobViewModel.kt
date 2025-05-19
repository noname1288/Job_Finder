package com.example.jobfinder.presentation.workspace.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.data.remote.dto.request.CreateJobRequest
import com.example.jobfinder.domain.entity.Shift
import com.example.jobfinder.presentation.BaseUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class CreateJobViewModel : ViewModel(){
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
        }
    }

    private fun createJob() {
        viewModelScope.launch {
            _stateCreateJob.value = _stateCreateJob.value.copy(isLoading = true)

            val request : CreateJobRequest = CreateJobRequest(
                title =_stateCreateJob.value.title,
                description = _stateCreateJob.value.description,
                requirement =_stateCreateJob.value.requirement,
                salary = _stateCreateJob.value.salary,
                benefit = _stateCreateJob.value.benefit,
                location = _stateCreateJob.value.location,
                numberOfPositions = _stateCreateJob.value.numberOfPositions,
                deadLine = TODO(), //24/05/2025
                shift = Shift(
                    name = _stateCreateJob.value.nameShift,
                    startTime =  TODO(),//09:00
                    endTime =  TODO()//11:59
                )
            )

        }
    }
}

data class CreatJobUIState(
    val title:String = "",
    val description:String = "",
    val requirement:String = "",
    val benefit:String = "",
    val salary:String = "",
    val location: String="",

    val numberOfPositions: String="", //convert into Int before send to server

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

}