package com.example.jobfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.jobfinder.presentation.BaseScreen
import com.example.jobfinder.ui.theme.JobFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JobFinderTheme {
                BaseScreen()
            }
        }
    }
}