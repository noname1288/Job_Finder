package com.example.jobfinder.feature.workspace.create


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobPage(navController: NavController) {
    val scrollState = rememberScrollState()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var requirement by remember { mutableStateOf("") }
    var benefit by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var workingTime by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Việc làm mới", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    TextButton(onClick = { /*TODO: post job*/ }) {
                        Text("Tạo", color = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            // Company Info (fake)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape),
                    color = Color.Gray.copy(alpha = 0.2f)
                ) {}
                Spacer(Modifier.width(8.dp))
                Column {
                    Text("Minh Hoang Digi Company", fontWeight = FontWeight.Bold)
                    Text("Đang tuyển dụng tại Hà Nội", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(Modifier.height(16.dp))
            LabeledInput("Tiêu đề", title) { title = it }
            LabeledInput("Mô tả", description, singleLine = false) { description = it }
            LabeledInput("Yêu cầu ứng viên", requirement, singleLine = false) { requirement = it }
            LabeledInput("Quyền lợi", benefit, singleLine = false) { benefit = it }
            LabeledInput("Địa điểm làm việc", address) { address = it }
            LabeledInput("Thời gian làm việc", workingTime) { workingTime = it }

            LabeledSelect("Cấp bậc")
            LabeledSelect("Hình thức làm việc")
            LabeledSelect("Thời gian làm việc")
            LabeledSelect("Số lượng tuyển")

            LabeledInput("Hạn đăng tuyển", deadline, trailingIcon = {
                Icon(Icons.Default.CalendarToday, contentDescription = null)
            }) { deadline = it }

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun LabeledInput(
    label: String,
    value: String,
    singleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)) {
        Text(label, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine,
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = if (singleLine) KeyboardType.Text else KeyboardType.Text
            ),
            placeholder = { Text("Write the title of your post here") }
        )
    }
}

@Composable
fun LabeledSelect(label: String) {
    Surface(
        tonalElevation = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(label, fontSize = 14.sp)
            Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
        }
    }
}

