package com.example.jobfinder.feature.candidate


import BoxBackground
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jobfinder.R
import com.example.jobfinder.navigation.AppRoutes

@Composable
fun CandidateManagementPage(navController: NavController) {
//    Column(modifier = Modifier.fillMaxSize()) {
//        BoxBackground("Công việc của bạn", "Hãy để chúng tôi trợ giúp", true, R.raw.vector_job)
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        CandidateTrackingSection(20,5)
//
//        Spacer(modifier = Modifier.height(12.dp))


        LazyColumn (modifier = Modifier.fillMaxSize()){
            item {
                BoxBackground("Công việc của bạn", "Hãy để chúng tôi trợ giúp", true, R.raw.vector_job)

                Spacer(modifier = Modifier.height(12.dp))

                CandidateTrackingSection(20,5)

                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                CandidateItem("03 tháng 2 năm 2025", "E-Learning", "3/5",navController)
                CandidateItem("01 tháng 2 năm 2025", "E-Learning1", "5/5", navController)
                CandidateItem("15 tháng 2 năm 2025", "E-Learning2", "7/5", navController)
            }
            item{
                Spacer(modifier = Modifier.height(12.dp))
            }
        }


//    }
}
@Composable
fun CandidateItem(
    date: String,
    title: String,
    progress: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp).clickable(){
                navController.navigate(AppRoutes.CANDIDATE_LIST)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // Nội dung thẻ
        Column(modifier = Modifier.padding(16.dp)) {
            // Hàng đầu chứa icon + ngày
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    tint = Color(0xFF7B61FF), // Màu tím
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = date,
                    fontSize = 14.sp,
                    color = Color(0xFF333333)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Hàng thứ hai: "Tiêu đề" và "progress"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Tiêu đề",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        color = Color(0xFF333333),
                        fontWeight = FontWeight.Medium
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Ứng viên",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = progress,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (progress == "7/5") Color.Red else Color(0xFF2EBD85)
                    )
                }
            }
        }
    }
}

@Composable
fun CandidateTrackingSection(
    countCandidate: Int,
    countJob: Int,
) {
    // Thẻ (Card) lớn bao quanh
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), // canh lề ngang
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        // Nội dung bên trong Card lớn
        Column(modifier = Modifier.padding(16.dp)) {
            // Tiêu đề
            Text(
                text = "Theo dõi số lượng ứng viên",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "1 Jan 2024 - 30 Dec 2024",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier
                    .padding(bottom = 12.dp)
            )

            // Hàng chứa 2 thẻ con (Card nhỏ)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Card thứ nhất
                Card(
                    modifier = Modifier
                        .weight(1f)            // Chia đều 1/2 chiều rộng
                        .height(60.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5EA)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFB8E7D2)
                    ),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    // Bên trong Card thứ nhất
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF2EBD85), shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Ứng viên", fontSize = 12.sp, color = Color.Black)
                        }
                        Text(
                            text = "$countCandidate",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp)) // Khoảng cách giữa 2 Card

                // Card thứ hai
                Card(
                    modifier = Modifier
                        .weight(1f)            // Chia đều 1/2 chiều rộng
                        .height(60.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5EA)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFEBE9FE)
                    ),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    // Bên trong Card thứ hai
                    Column(
                        modifier = Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF7B61FF), shape = CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Công việc", fontSize = 12.sp, color = Color.Black)
                        }
                        Text(
                            text = "$countJob",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}
