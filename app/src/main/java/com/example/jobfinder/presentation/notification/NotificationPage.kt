package com.example.jobfinder.presentation.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jobfinder.utils.MyColorUtils
import com.example.jobfinder.utils.Utils
import com.example.jobfinder.utils.component.NotificationItem
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


@Composable
fun NotificationPage(navController: NavController, viewModel: NotificationViewModel) {
    val stateNotiPage by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchNoti()
    }

    LazyColumn {
        if (stateNotiPage.notiList.isEmpty()){
            item { Text("Không có thông báo nào") }
        }else {
            items(stateNotiPage.notiList) { noti ->
                NotificationItem(noti, showTime(noti.createdAt))
                HorizontalDivider(
                    color = MyColorUtils.Grey300,
                    thickness = 1.dp
                ) // Thêm đường gạch ngang sau mỗi thông báo
            }
        }

    }
}

fun showTime(input: String): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        val inputTime = LocalDateTime.parse(input, formatter)
        val now = LocalDateTime.now()
        val duration = Duration.between(inputTime, now)

        when {
            duration.toHours() >= 24 -> inputTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            duration.toHours() >= 1 -> "${duration.toHours()} giờ trước"
            duration.toMinutes() >= 1 -> "${duration.toMinutes()} phút trước"
            else -> "Vừa xong"
        }
    } catch (e: Exception) {
        "Thời gian không hợp lệ"
    }
}




