package com.example.jobfinder.presentation.notification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.data.local.UserSessionManager
import com.example.jobfinder.data.remote.dto.response.NotificationResponse
import com.example.jobfinder.domain.repository.NotificationRepository
import com.example.jobfinder.presentation.BaseUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel(private val notiRepo: NotificationRepository) : ViewModel() {
    private val _state = MutableStateFlow(NotificationUIState())
    val state: StateFlow<NotificationUIState> = _state

    init {
        fetchNoti()
    }

    fun fetchNoti() {
        viewModelScope.launch {
            _state.value = NotificationUIState(isLoading = true)

            val request = UserSessionManager.getUserId()
            val result = notiRepo.getAllNotifications(request)

            Log.d("NotificationViewModel", "fetchNoti: $result")

            when (result) {
                is NetworkResult.Error -> {
                    _state.value = _state.value.copy(isLoading = false, errorMessage = result.message)
                }

                is NetworkResult.Success->{
                    _state.value = _state.value.copy(isLoading = false, isSuccess = true, notiList = result.data)
                }
            }
        }
    }

    fun resetState(){
        _state.value = _state.value.copy(
            isLoading = false,
            isSuccess = false,
            errorMessage = null,
            notiList = emptyList()
        )
    }
}

data class NotificationUIState(
    override val errorMessage: String? = null,
    override val isLoading: Boolean = false,
    override val isSuccess: Boolean = false,
    val notiList: List<NotificationResponse> = emptyList()
) : BaseUIState()

