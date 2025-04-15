package com.example.jobfinder.feature.notification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jobfinder.utils.component.NotificationItem
import com.example.jobfinder.utils.MyColorUtils


@Composable
fun NotificationPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(innerPadding),

        ) {
        // tap layout
        TabLayoutNotification()
    }
}

@Composable
fun TabLayoutNotification() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var tabTitleList = listOf("Tin nhắn", "Thông báo")
    Column {
        NotiListContent()
    }
}

@Composable
fun NotiListContent(modifier: Modifier = Modifier) {
    val notiList = 1..10
    LazyColumn {
        items(notiList.count()) {
            NotificationItem()
            Divider(color = MyColorUtils.Grey300, thickness = 1.dp) // Thêm đường gạch ngang sau mỗi thông báo
        }
    }
}

