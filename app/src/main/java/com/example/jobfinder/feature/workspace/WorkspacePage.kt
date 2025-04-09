package com.example.jobfinder.feature.workspace

import BoxBackground
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.jobfinder.component.PostCard
import com.example.jobfinder.utils.MyColorUtils
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController


@Composable
fun WorkspacePage(navController: NavHostController) {
    val events = 1..10
    Column(
        modifier = Modifier
//            .padding(innerPadding)
            .fillMaxSize()
    ) {
        BoxBackground("Quản lý thời gian", "Đừng bỏ lỡ điều gì nhé")

        // tap layout
        TabLayout()

        //post list
        Box(modifier = Modifier.weight(0.5f)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MyColorUtils.Grey200),
            ) {
                items(events.count()) {
                    PostCard(navController)
                }
            }

        }
    }
}

@Composable
fun TabLayout(modifier: Modifier = Modifier) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var tabTitleList = listOf ("Bài đăng", "Công việc")
    Column {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabTitleList.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index, // Xác định tab nào được chọn
                    onClick = { selectedTabIndex = index }, // Cập nhật khi người dùng nhấn tab
                    text = { Text(text = title) } // Nội dung của mỗi tab
                )
            }
        }
        // Hiển thị nội dung của tab đang được chọn
        when (selectedTabIndex) {
            0 -> Text("Nội dung của Tab 1")
            1 -> Text("Nội dung của Tab 2")
        }
    }
}

