package com.example.jobfinder.presentation.workspace

import BoxBackground
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jobfinder.R
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.navigation.AppRoutes
import com.example.jobfinder.navigation.navigateWithArgs
import com.example.jobfinder.utils.Utils


/**
 * Composable function for the Workspace page.
 *
 * This page displays a list of jobs retrieved from the WorkspaceViewModel.
 * It features a background with a title and subtitle, and a lazy column to efficiently display the job cards.
 * If no jobs are found, a "Not found any jobs" message is shown.
 *
 * @param navController The NavController used for navigation actions, such as navigating to job details.
 * @param workspaceViewModel The ViewModel responsible for managing workspace-related data, including fetching and holding the list of jobs.
 */
@Composable
fun WorkspacePage(navController: NavController, workspaceViewModel: WorkspaceViewModel) {
    val context = LocalContext.current

    val stateWorkspace by workspaceViewModel.stateWorkspace.collectAsState()
    var isShowLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        workspaceViewModel.getAllJobs()
    }

    Column(
        modifier = Modifier
//            .padding(innerPadding)
            .fillMaxSize()
    ) {
        BoxBackground("Quản lý công việc", "Đừng bỏ lỡ điều gì nhé", false, R.drawable.alarm_clock)

        LazyColumn {
            if (stateWorkspace.allJobs.isEmpty()) {
                item { Text(text = "Not found any jobs") }
            } else {
                items(stateWorkspace.allJobs) { job ->
                    JobCard(job, navController)
                }
            }

        }
    }
}

@Composable
fun JobCard(job: Job, navController: NavController) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = showColorCard(job), // màu nền bạn muốn
            contentColor = Color.Black           // màu chữ hoặc icon mặc định bên trong
        ),
        modifier = Modifier
            .padding(12.dp)
            .clickable(onClick = {
                val jobIdString = job.id.toString()
                navController.navigateWithArgs(
                    route = AppRoutes.JOB_DETAIL,
                    args = arrayOf(jobIdString),
                    popUpToRoute = AppRoutes.WORK_SPACE,
                    isInclusive = false,
                    restore = false)
            }),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            Spacer(modifier = Modifier.height(6.dp))

            //Job title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = job.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(230.dp),
                    maxLines = 2
                )
                Text(
                    text = showTextStatus(job.status),
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic
                )

            }


            //date
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
//                    .border(1.dp, MyColorUtils.Grey200, RoundedCornerShape(10.dp)),

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //thời gian thực thi công việc
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(120.dp)
                    ) {
                        Text("Bắt đầu", fontSize = 12.sp)
                        Text(
                            "${job.shift.startTime!!.format(Utils.formatter_Hour_Minus)}",
                            fontSize = 16.sp
                        )
                    }
                    //ca làm việc
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(120.dp)
                    ) {
                        Text("Kết ca", fontSize = 12.sp)
                        Text(
                            "${job.shift.endTime!!.format(Utils.formatter_Hour_Minus)}",
                            fontSize = 16.sp
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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
                            text = job.location,
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
                        //số lượng người làm
                        Text(
                            text = job.numberOfPositions.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF1A1A1A) // gần với màu chữ trong ảnh
                        )
                    }
                }

                //Thời gian kết thúc công việc đó
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
                    Text(
                        text = "Kết thúc: ${Utils.localDateTimeToString(job.endAt)}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

private fun showColorCard(job: Job): Color {
    return when (job.status) {
        "OPEN" -> Color(0xFFC2B2D0)
        "WORKING" -> Color(0xFFF8D3CF)
        "CLOSE" -> Color(0xFFE7E3E0)
        "PENDING" -> Color(0xFFF6F6F3)
        else -> Color(0xFF53535A)
    }
}

private fun showTextStatus(jobStatus: String): String {
    return when (jobStatus) {
        "OPEN" -> "Đang tuyển"
        "WORKING" -> "Đang làm"
        "CLOSE" -> "Đã đóng"
        "PENDING" -> "Chờ duyệt"
        else -> "NOTHING"
    }
}


