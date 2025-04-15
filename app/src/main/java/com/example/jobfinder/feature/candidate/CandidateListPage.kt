package com.example.jobfinder.feature.candidate

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jobfinder.data.entity.Candidate
import com.example.jobfinder.data.entity.sampleCandidates

enum class StatusDialog {
    NONE,
    ACCEPT,
    REJECT
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CandidateListPage(
    navController: NavController
) {
    var candidates = sampleCandidates
    var showDialog by remember { mutableStateOf<StatusDialog>(StatusDialog.NONE) }


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {navController.popBackStack()}) {
                        Icon(Icons.Rounded.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                title = { Text("Danh sách ứng viên") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
// Danh sách ứng viên
            items(candidates) { candidate ->
                CandidateItem(
                    candidate = candidate,
                    onAccept = {
                        showDialog = StatusDialog.ACCEPT
                    },
                    onReject = {
                        showDialog = StatusDialog.REJECT
                    }
                )
                when (showDialog) {
                    StatusDialog.ACCEPT -> {
                        AlertDialogExample(
                            onDismissRequest = { showDialog = StatusDialog.NONE },
                            onConfirmation = {
                                showDialog = StatusDialog.NONE
                                println("Confirmation registered") // Add logic here to handle confirmation.
                            },
                            dialogTitle = "Duyệt",
                            dialogText = "Bạn đã thấy ứng viên ${candidate.name} đạt điều kiện",
                            icon = Icons.Default.Info
                        )
                    }

                    StatusDialog.REJECT -> {
                        AlertDialogExample(
                            onDismissRequest = { showDialog = StatusDialog.NONE },
                            onConfirmation = {
                                showDialog = StatusDialog.NONE
                                println("Confirmation registered") // Add logic here to handle confirmation.
                            },
                            dialogTitle = "Từ chối",
                            dialogText = "Bạn có chắn chắn muốn từ chối ứng viên ${candidate.name} ",
                            icon = Icons.Default.Warning
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}


@Composable
fun CandidateItem(
    candidate: Candidate,
    onAccept: () -> Unit = {},
    onReject: () -> Unit = {}
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            // Hàng 1: show in4 của candidate
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // 1) Ảnh đại diện
                Image(
                    painter = painterResource(id = candidate.avatarRes),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                // 2) Thông tin ứng viên
                Column() {
                    // Tên và tick xanh
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = candidate.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                        if (candidate.isVerified) {
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Verified",
                                tint = Color(0xFF7B61FF),
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    // Ngày sinh
                    Text(
                        text = candidate.birthDate,
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    // Địa chỉ
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color.Gray,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = candidate.location,
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            // Hàng 2: show button : "Chấp nhận"/ "Từ chối"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),

                horizontalArrangement = Arrangement.Center,
            ) { // Nút chấp nhận
                Button(
                    onClick = onAccept,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF7B61FF)
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(0.3f)
                ) {
                    Text(text = "Chấp nhận", fontSize = 12.sp, color = Color.White)
                }

                Spacer(Modifier.width(16.dp))


                // Nút từ chối
                Button(
                    onClick = onReject,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    border = BorderStroke(1.dp, Color.Red),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(0.3f)
                ) {
                    Text(text = "Từ chối", fontSize = 12.sp, color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Đồng ý")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Hủy")
            }
        }
    )
}
