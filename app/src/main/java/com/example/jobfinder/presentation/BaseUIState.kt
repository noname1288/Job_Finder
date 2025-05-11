package com.example.jobfinder.presentation

open class BaseUIState(
    open val isLoading: Boolean = false,
    open val errorMessage: String? = null,
    open val isSuccess: Boolean = false
)