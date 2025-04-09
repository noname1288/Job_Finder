package com.example.jobfinder.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import com.example.jobfinder.model.FakeData.mockPosts
import com.example.jobfinder.model.PostModel
import com.example.jobfinder.R


@Composable
fun HomePage() {
    // Dùng LazyColumn nếu nội dung dài, cần scroll
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        /** TOP BAR + AVATAR + TÊN + VAI TRÒ */
        item {
            HomeHeader()
        }

        /** THẺ CHÀO NGÀY MỚI */
        item {
            GreetingCard()
        }

        /** TỔNG QUAN (ĐANG TUYỂN, ĐANG XỬ LÝ, ĐÃ HOÀN THÀNH) */
        item {
            OverviewSection()
        }

        /** DANH SÁCH BÀI ĐĂNG */
        item {
            PostListSection()
        }

        // Hoặc nếu bạn muốn load dữ liệu động, dùng:
        // items(listOfData) { post -> ... }
        // Ở đây demo sẵn mockPosts
        items(mockPosts) { post ->
            PostItem(post)
        }

        // Thêm khoảng trống ở cuối
        item {
            Spacer(Modifier.height(16.dp))
        }
    }
}

/* ----------------------------------------------------------------
   TOP BAR (ví dụ đơn giản, hiển thị thời gian + avatar + icon)
---------------------------------------------------------------- */
@Composable
fun HomeHeader() {
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
                Text("Tonald Drump", style = MaterialTheme.typography.bodyMedium)
                Text(
                    "Junior Full Stack Developer",
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                )
            }
        }

        // Icon Message / Notification
        Row {
            IconButton(onClick = { /* TODO: Action search */ }) {
                Icon(Icons.Default.Search, contentDescription = "search")
            }
            IconButton(onClick = { /* TODO: Action notification */ }) {
                Icon(Icons.Default.Notifications, contentDescription = "notifications")
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
fun OverviewSection() {
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
                text = "Tất cả công việc của bạn",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
            )
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OverviewItem(title = "Đang tuyển", number = 5, color = Color(0xFF906CF2))
                OverviewItem(title = "Đang xử lý", number = 2, color = Color(0xFFF2994A))
                OverviewItem(title = "Đã hoàn thành", number = 1, color = Color(0xFF27AE60))
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
fun PostListSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Bài đăng (2)",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            )
        )
        Text(
            text = "Số bài đăng trong tháng",
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
        )
        Spacer(Modifier.height(8.dp))
    }
}

/* ----------------------------------------------------------------
   POST ITEM (hiển thị mỗi bài đăng)
---------------------------------------------------------------- */
@Composable
fun PostItem(post: PostModel) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(SvgDecoder.Factory())
        }
        .build()
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
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
            Spacer(Modifier.height(8.dp))

            // Thanh progress (demo)
            LinearProgressIndicator(
                progress = post.progress, // 0f..1f
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                color = Color(0xFF906CF2),
                trackColor = Color.LightGray.copy(alpha = 0.3f)
            )

            Spacer(Modifier.height(8.dp))
            // Thông tin: Địa điểm, số ứng viên, hạn
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
                            text = post.location,
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
                            text = post.candidateCount.toString() + " Ứng viên",
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
                    Text(text = post.deadline, style = MaterialTheme.typography.bodyMedium)
                }
            }



        }
    }
    Spacer(Modifier.height(4.dp))
    Divider(thickness = 0.dp, color = Color.Transparent) // chỉ để tạo khoảng trống
}
