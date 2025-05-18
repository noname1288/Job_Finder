package com.example.jobfinder.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import com.example.jobfinder.R
import com.example.jobfinder.data.local.UserSessionManager
import com.example.jobfinder.domain.entity.FakeData.mockPosts
import com.example.jobfinder.domain.entity.PostModel
import com.example.jobfinder.navigation.AppRoutes
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.modifier.modifierLocalConsumer
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.domain.entity.calculatePregress
import com.example.jobfinder.service_locator.AppContainer


@Composable
fun HomePage(navController: NavController, homeViewModel: HomeViewModel) {
    // Dùng LazyColumn nếu nội dung dài, cần scroll
    val stateHome by homeViewModel.stateHome.collectAsState()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        /** TOP BAR + AVATAR + TÊN + VAI TRÒ */
        item {
            HomeHeader(navController)
        }

        /** THẺ CHÀO NGÀY MỚI */
        item {
            GreetingCard()
        }

        /** TỔNG QUAN (ĐANG TUYỂN, ĐANG XỬ LÝ, ĐÃ HOÀN THÀNH) */
        item {
            OverviewSection(
                openCount = stateHome.openJob,
                closeCount = stateHome.closeJob,
                workingCount = stateHome.workingJob
            )
        }

        /** DANH SÁCH BÀI ĐĂNG */
        item {
            PostListSection(stateHome.listJobs.size)
        }

        // Hoặc nếu bạn muốn load dữ liệu động, dùng:
        // items(listOfData) { post -> ... }
        // Ở đây demo sẵn mockPosts
        items(stateHome.listJobs) { job ->
            LinerProgressPostItem(job, navController)
        }

        // Thêm khoảng trống ở cuối
        item {
            Spacer(Modifier.height(16.dp))
        }
    }
}

/* ----------------------------------------------------------------
   TOP BAR (hiển thị avatar + icon Search + message)
---------------------------------------------------------------- */
@Composable
fun HomeHeader(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Avatar + Tên + Role
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Avatar
            Surface(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("TD", color = MaterialTheme.colorScheme.onPrimaryContainer)
                }
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text(UserSessionManager.getUserName().ifEmpty { "Not found User" }, style = MaterialTheme.typography.bodyMedium)
                Text(
                    "Company: ${UserSessionManager.getEmail().ifEmpty { "Not found infomation" }}",
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                )
            }
        }

        // Icon Message / Notification
        Row {
            IconButton(onClick = { /* TODO: Action search */ }) {
                Icon(Icons.Default.Search, contentDescription = "search")
            }
            IconButton(onClick = {
                navController.navigate(AppRoutes.MESSAGE)
            }) {
                Icon(Icons.Outlined.Mail, contentDescription = "notifications")
            }

        }
    }
}

/* ----------------------------------------------------------------
   GREETING CARD (Chào ngày mới)
---------------------------------------------------------------- */
@Composable
fun GreetingCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFB8A7F2) // Màu tím tuỳ chọn
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Nội dung text
            Column(Modifier.weight(1f)) {
                Text(
                    text = "Chào ngày mới",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = "Chúc mọi điều tốt lành",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White)
                )
            }
            // Icon minh hoạ
            Surface(
                modifier = Modifier.size(48.dp),
                color = Color(0xFFFFFFFF).copy(alpha = 0.2f),
                shape = CircleShape
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("☁", color = Color.White)
                }
            }
        }
    }

    Spacer(Modifier.height(8.dp))
}

/* ----------------------------------------------------------------
   OVERVIEW SECTION (Tổng quan 3 ô: Đang tuyển - Đang xử lý - Đã hoàn thành)
---------------------------------------------------------------- */
@Composable
fun OverviewSection(openCount: Int, closeCount: Int, workingCount: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Tổng quan",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                text = "Tất cả công việc của bạn trong tháng này",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OverviewItem(title = "Đang tuyển", number = openCount, color = Color(0xFF906CF2))
                OverviewItem(title = "Đang xử lý", number = workingCount, color = Color(0xFFF2994A))
                OverviewItem(title = "Đã hoàn thành", number = closeCount, color = Color(0xFF27AE60))
            }
        }
    }
    Spacer(Modifier.height(8.dp))
}

@Composable
fun OverviewItem(title: String, number: Int, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Vòng tròn hiển thị số
        Surface(
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            color = color.copy(alpha = 0.1f)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "$number",
                    style = MaterialTheme.typography.bodyLarge.copy(color = color)
                )
            }
        }
        Spacer(Modifier.height(4.dp))
        Text(title, style = MaterialTheme.typography.labelMedium)
    }
}

/* ----------------------------------------------------------------
   POST LIST SECTION (Tiêu đề "Bài đăng (2)" + mô tả)
---------------------------------------------------------------- */
@Composable
fun PostListSection(sizeOfListJob: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Bài đăng $sizeOfListJob",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = "Bạn đang tìm ứng viên cho công việc mới",
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
        )
        Spacer(Modifier.height(8.dp))
    }
}

/* ----------------------------------------------------------------
   POST ITEM (hiển thị mỗi bài đăng)
---------------------------------------------------------------- */
@Composable
fun LinerProgressPostItem(post: Job, navController: NavController) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = {
                navController.navigate(AppRoutes.JOB_DETAIL)
            }),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Tiêu đề bài đăng
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape),
                    color = Color(0xFF906CF2).copy(alpha = 0.1f)
                ) {
                    Box(contentAlignment = Alignment.Center) {
//                        Icon(imageVector = ImageVector.vectorResource(id = R.drawable.vector_flash_24))
                        AsyncImage(
                            model = R.raw.vector_flash_24, // hoặc file trong res/raw
                            contentDescription = "SVG icon",
                            imageLoader = imageLoader
                        )
                    }
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    maxLines = 1,
                    fontSize = 16.sp
                )
            }
            Spacer(Modifier.height(16.dp))

//            // Thanh progress (demo)
            LinearProgressIndicator(
                progress = {
                    post.calculatePregress() // 0f..1f
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(4.dp),
                color = Color(0xFF906CF2),
                trackColor = Color.LightGray.copy(alpha = 0.3f),
            )

            Spacer(Modifier.height(16.dp))
            // Thông tin: Địa điểm, số ứng viên, hạn
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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
                        text = post.location,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF1A1A1A) // gần với màu chữ trong ảnh
                        ,maxLines = 1
                    )
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
                    Text(text = AppContainer.localDateTimeToString(post.endAt), style = MaterialTheme.typography.bodyMedium)
                }


            }

            Spacer(Modifier.height(6.dp))

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
                    text = post.candidateCount.toString() + " Ứng viên",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF1A1A1A) // gần với màu chữ trong ảnh
                    ,maxLines = 1

                )
            }


        }
    }
    Spacer(Modifier.height(8.dp))
    HorizontalDivider(thickness = 0.dp, color = Color.Transparent) // chỉ để tạo khoảng trống
}
