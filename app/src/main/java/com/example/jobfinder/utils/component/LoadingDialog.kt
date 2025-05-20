package com.example.jobfinder.utils.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun LoadingDialog() = Dialog(onDismissRequest = {}) {
    Dialog(onDismissRequest = { /*không cho tắt*/ }) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    }
}
