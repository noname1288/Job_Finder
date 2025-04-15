package com.example.jobfinder.feature.workspace

import BoxBackground
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jobfinder.R
import com.example.jobfinder.data.entity.FakeData.mockPosts
import com.example.jobfinder.feature.home.LinerProgressPostItem
import com.example.jobfinder.navigation.AppRoutes
import com.example.jobfinder.utils.MyColorUtils


@Composable
fun WorkspacePage(navController: NavHostController) {
    val events = 1..10
    Column(
        modifier = Modifier
//            .padding(innerPadding)
            .fillMaxSize()
    ) {
        BoxBackground("Quản lý thời gian", "Đừng bỏ lỡ điều gì nhé", false, R.drawable.alarm_clock)

        // tap layout
        TabLayout(navController = navController)

//        //job list == (job <- post chuyen trang thai)
//        Box(modifier = Modifier.weight(0.5f)) {
//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(MyColorUtils.Grey200),
//            ) {
//                items(events.count()) {
//                    PostCard(navController)
//                }
//            }
//
//        }
    }
}

@Composable
fun TabLayout(modifier: Modifier = Modifier, navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    var tabTitleList = listOf("Đang tuyển", "Thực thi")
    Column() {
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
            0 -> {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    mockPosts.forEach { postItem -> LinerProgressPostItem(postItem, navController) }
                }
            }

            1 -> {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    for (i in 1..5){
                        JobCard(navController)
                    }
                }
            }
        }
    }
}


@Composable
fun JobCard(navController: NavController) {
    ElevatedCard(
        modifier = Modifier
            .padding(12.dp)
            .clickable(onClick = {
                navController.navigate(AppRoutes.JOB_DETAIL)
            }),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            Spacer(modifier = Modifier.height(6.dp))

            //Job title
            Text(text = "Tuyển nhân viên bán hàng", fontSize = 16.sp, fontWeight = FontWeight.Bold)


            //date
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
//                    .border(1.dp, MyColorUtils.Grey200, RoundedCornerShape(10.dp)),

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Text("Tong thoi gian", fontSize = 12.sp)
                        Text("08:00:00 hrs", fontSize = 16.sp)
                    }
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        Text("Bat dau - Ket thuc", fontSize = 12.sp)
                        Text("09:00 AM - 05:00 PM ", fontSize = 16.sp)
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column(modifier = Modifier.weight(0.5f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_warehouse_16), // vector drawable
                            contentDescription = null,
                            tint = Color(0xFFCBD2E1), // màu xám nhạt giống ảnh
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "post.location",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF1A1A1A) // gần với màu chữ trong ảnh
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_emoji_normal_16), // vector drawable
                            contentDescription = null,
                            tint = Color(0xFFCBD2E1), // màu xám nhạt giống ảnh
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "So luong: __",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF1A1A1A) // gần với màu chữ trong ảnh
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar_grey_16), // vector drawable
                        contentDescription = null,
                        tint = Color(0xFFCBD2E1), // màu xám nhạt giống ảnh
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Het han: __", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}


