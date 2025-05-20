package com.example.jobfinder.utils.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.jobfinder.R
import com.example.jobfinder.data.remote.dto.response.NotificationResponse
import com.example.jobfinder.utils.Utils
import kotlinx.coroutines.launch
import java.time.LocalDateTime

import java.time.*
import java.time.format.DateTimeFormatter
import kotlin.math.abs

@Composable
fun NotificationItem(noti: NotificationResponse, notiCreateTime: String) {

    Row(
        Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Bạn có 1 thông báo mới", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = notiCreateTime , fontSize = 12.sp)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = noti.message,
                fontSize = 16.sp,
                maxLines = 2,
                style = TextStyle(lineHeight = 16.sp),
                lineHeight = 1.3.em,
            )
        }
    }
}
