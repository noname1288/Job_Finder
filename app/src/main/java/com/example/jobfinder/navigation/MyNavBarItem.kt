package com.example.jobfinder.utils

import androidx.compose.ui.graphics.vector.ImageVector

data class MyNavBarItem(
    val label: String,
    val icon: ImageVector,
    val badge: Int = 0,
    val route: String
)
