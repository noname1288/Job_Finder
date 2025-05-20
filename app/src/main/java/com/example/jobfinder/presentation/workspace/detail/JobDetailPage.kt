package com.example.jobfinder.pages.jobdetail

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jobfinder.domain.entity.Job
import com.example.jobfinder.navigation.AppRoutes
import com.example.jobfinder.navigation.navigateWithArgs
import com.example.jobfinder.navigation.popBackIfCan
import com.example.jobfinder.navigation.safeNavigate
import com.example.jobfinder.presentation.workspace.create.CreateJobEvent
import com.example.jobfinder.presentation.workspace.detail.JobDetailEvent
import com.example.jobfinder.presentation.workspace.detail.JobDetailViewModel
import com.example.jobfinder.utils.StatusJob
import kotlinx.coroutines.launch

enum class BottomSheetType {
    EDIT_CONTENT,
    UPDATE_STATUS,
    DELETE,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobDetailPage(
    navController: NavController,
    jobId: String,
    jobDetailViewModel: JobDetailViewModel
) {
    Log.d("JobDetailPage", "jobId: $jobId")
    val context = LocalContext.current

    val stateJobDetail by jobDetailViewModel.stateJobDetail.collectAsState()

    LaunchedEffect(jobId) {
        //update ID
        jobDetailViewModel.onEvent(JobDetailEvent.JobIdChanged(jobId))
        // fetch data
        jobDetailViewModel.onEvent(JobDetailEvent.GetJobDetailById)
    }

    LaunchedEffect(key1 = stateJobDetail.isDeleted) {
        if (stateJobDetail.isDeleted) {
            navController.popBackStack()
            jobDetailViewModel.onEvent(JobDetailEvent.ResetState)
        }
    }

    LaunchedEffect(stateJobDetail.errorMessage == null) {
        stateJobDetail.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    var expanded by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState() // quan ly state cua bottomsheet
    val scope = rememberCoroutineScope() //tu tao scope de su dung coroutine
    var showBottomSheet by remember { mutableStateOf<BottomSheetType?>(null) }
    var selectedOption by remember { mutableStateOf(StatusJob.list[0]) }

    // Nếu muốn top bar hiển thị nút back
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Job Detail") },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        colors = iconButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        expanded = true
                    }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Show menu item to CRUD job")
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }) {
                        // Sửa nội dung
//                        DropdownMenuItem(
//                            text = { Text("Sửa nội dung") },
//                            onClick = {
//                                expanded = false
//                                showBottomSheet = BottomSheetType.EDIT_CONTENT
//                            }
//                        )
                        // Trạng thái
//                        DropdownMenuItem(
//                            text = { Text("Trạng thái") },
//                            onClick = {
//                                expanded = false
//                                showBottomSheet = BottomSheetType.UPDATE_STATUS
////                                onUpdateStatus()
//                            }
//                        )
                        // Xem ứng viên
                        DropdownMenuItem(
                            text = { Text("Xem ứng viên") },
                            onClick = {
                                expanded = false

                                navController.navigateWithArgs(
                                    route = AppRoutes.CANDIDATE_LIST,
                                    args = arrayOf(jobId),
                                    popUpToRoute = String.format(AppRoutes.JOB_DETAIL, jobId), // quan trọng nè!
                                    isInclusive = false,
                                    restore = false
                                )
//                                showBottomSheet = BottomSheetType.VIEW_CANDIDATES
//                                onViewCandidates()
                            }
                        )
                        HorizontalDivider()
                        // Xoá
                        DropdownMenuItem(
                            text = { Text("Xoá") },
                            onClick = {
                                expanded = false
                                showBottomSheet = BottomSheetType.DELETE
//                                onDelete()
                            }
                        )
                    }
                }

            )
        }
    ) { innerPadding ->
        // Nội dung chính
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // 1) Logo + Tiêu đề
            HeaderSection(stateJobDetail.detailJob)

            Spacer(Modifier.height(8.dp))

            // 2) Dòng thông tin (lương, địa điểm, kinh nghiệm, thời gian đăng)
            InfoRowSection(stateJobDetail.detailJob)

            Spacer(Modifier.height(16.dp))

            // 3) Nội dung chi tiết

            // Mô tả công việc
            CustomeTextHeading("Mô tả công việc")
            Spacer(Modifier.height(6.dp))

            CustomeTextDescription(stateJobDetail.detailJob.description)
            Spacer(Modifier.height(12.dp))

            // Yêu cầu
            CustomeTextHeading("Yêu cầu")
            Spacer(Modifier.height(6.dp))

            CustomeTextDescription(stateJobDetail.detailJob.requirement)
            Spacer(Modifier.height(12.dp))

            // Quyền lợi
            CustomeTextHeading("Quyền lợi")
            Spacer(Modifier.height(6.dp))

            CustomeTextDescription(stateJobDetail.detailJob.benefit)
            Spacer(Modifier.height(12.dp))

            //Số lượng tuyển
            CustomeTextHeading("Số lượng tuyển")
            Spacer(Modifier.height(6.dp))

            CustomeTextDescription(stateJobDetail.detailJob.numberOfPositions.toString())
            Spacer(Modifier.height(12.dp))

            // ca làm
            CustomeTextHeading("Ca làm")
            Spacer(Modifier.height(6.dp))

            CustomeTextDescription("Bắt đầu: " + stateJobDetail.detailJob.shift.startTime.toString())
            Spacer(Modifier.height(12.dp))
            CustomeTextDescription("Kết thúc: " + stateJobDetail.detailJob.shift.endTime.toString())
            Spacer(Modifier.height(12.dp))

            //bottomSheet
            when (showBottomSheet) {
                BottomSheetType.EDIT_CONTENT -> {}

                BottomSheetType.UPDATE_STATUS -> {
                    ModalBottomSheet(
                        sheetState = sheetState, onDismissRequest = { showBottomSheet = null }
                    ) {
                        StatusBottomSheetContent(
                            radioOptions = StatusJob.list,
                            selectedOption = selectedOption,
                            onOptionSelected = { selectedOption = it },
                            onApply = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = null
                                    }

                                    Log.d("JobDetailPage", "selectedOption: $selectedOption")
                                    when (selectedOption){
                                        StatusJob.processing -> {

                                        }
                                        StatusJob.hiring -> {}
                                        StatusJob.complete -> {}
                                    }

                                }
                            }
                        )
                    }
                }

                BottomSheetType.DELETE -> {
                    ModalBottomSheet(
                        sheetState = sheetState, onDismissRequest = { showBottomSheet = null }
                    ) {
                        DeleteBottomSheetContent(
                            onCancel = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = null
                                    }
                                }
                            },
                            onApply = {
                                scope.launch {
                                    jobDetailViewModel.onEvent(JobDetailEvent.DeleteJob)
                                    sheetState.hide()
                                    showBottomSheet = null
                                }
                            }
                        )
                    }
                }

                null -> {/*khong hien thi*/ }
            }

        }
    }
}

@Composable
fun CustomeTextHeading(title: String) {
    Text(
        title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun CustomeTextDescription(description: String) {
    Text(
        description,
        style = MaterialTheme.typography.bodyMedium
    )
}

@Composable
fun DeleteBottomSheetContent(
    onCancel: ()-> Unit,
    onApply: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            "Hoành thành công việc", style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Bạn thực sự muốn kết thúc nó?",
            maxLines = 2,
            style = TextStyle(
                color = Color.Gray,
                lineHeight = 2.em
            ),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(64.dp))
        Button(onClick = onApply, modifier = Modifier.width(250.dp)) {
            Text("Có")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onCancel, modifier = Modifier.width(250.dp)) {
            Text("Không")
        }


    }
}

/* ----------------------------------------------------------------
   StatusBottomSheetContent --- giao dien bottomsheet_ update status
---------------------------------------------------------------- */

@Composable
fun StatusBottomSheetContent(
    radioOptions: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    onApply: () -> Unit
) {
    // Mô phỏng layout
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            "Trạng thái công việc", style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Quyết định thay đổi trạng thái giúp bạn kiểm soát tiến trình công việc",
            maxLines = 2,
            style = TextStyle(
                color = Color.Gray,
                lineHeight = 2.em
            ),
            textAlign = TextAlign.Center

        )

        Spacer(Modifier.height(24.dp))

        // ... RadioButtonGroup "Đang tuyển", "Đang xử lý", "Hoàn thành" ...
        Column(Modifier.selectableGroup()) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { onOptionSelected(text) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = (text == selectedOption), onClick = null)
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }

        //button ---- thay doi --- confirm chuyen doi status
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onApply,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Thay đổi")
        }
    }
}

/* ----------------------------------------------------------------
   1) HeaderSection - Logo + Tiêu đề công việc
---------------------------------------------------------------- */
@Composable
fun HeaderSection(job: Job) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        // Logo công ty (demo)
        Surface(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        ) {
            // Ở đây thay bằng painterResource(R.drawable.google_logo) nếu có
            Box(contentAlignment = Alignment.Center) {
                Text("G", style = MaterialTheme.typography.titleLarge, color = Color.Gray)
            }
        }
        Spacer(Modifier.width(12.dp))

        Column {
            Text(
                job.title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                job.recruiter,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
        }
    }
}

/* ----------------------------------------------------------------
   2) InfoRowSection - dòng info (lương, địa điểm, kinh nghiệm, thời gian đăng)
---------------------------------------------------------------- */
@Composable
fun InfoRowSection(job: Job) {
    // Thông tin bạn đưa vào (18-25 triệu, California, Hà Nội, 3 năm, 1 day ago)
    // Tuỳ chỉnh format...
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(
            modifier = Modifier
                .weight(1f) // Chiếm phần không gian bằng nhau
                .padding(8.dp)
        ) {
            Text(
                "Lương",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                job.salary,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f) // Chiếm phần không gian bằng nhau
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Vị trí",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                job.location,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )
        }
        Column(
            modifier = Modifier
                .weight(1f) // Chiếm phần không gian bằng nhau
                .padding(8.dp) // Tùy chọn thêm khoảng cách trong các cột
            , horizontalAlignment = Alignment.End
        ) {
            Text(
                "1 day ago",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

/* ----------------------------------------------------------------
   3) LocationAndOtherSection - phần “Địa điểm”, thời gian làm việc, lương, v.v.
---------------------------------------------------------------- */
@Composable
fun LocationAndOtherSection(job: Job) {
    Text("Địa điểm", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
    Spacer(Modifier.height(6.dp))

    // Mẫu text
    Text(
        "• Thời gian làm việc: 8h - 17h\n" +
                "• Lương tuyển: 18-25 triệu (thoả thuận)\n" +
                "• Số lượng tuyển: 3 người\n" +
                "• Trạng thái: Đang tuyển\n" +
                "• Hạn nộp hồ sơ: 28/03/2025",
        style = MaterialTheme.typography.bodyMedium
    )
}
