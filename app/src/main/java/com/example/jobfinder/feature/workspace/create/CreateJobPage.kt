package com.example.jobfinder.feature.workspace.create

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobPage(navController: NavController) {
    val scrollState = rememberScrollState()

    var showDatePicker by remember { mutableStateOf(false) } // show dialog chọn deadline
    var showDatePickerRange by remember { mutableStateOf(false) } // show dialog chọn range: thời gian làm việc


    var title by remember { mutableStateOf("") }//tên công việc
    var description by remember { mutableStateOf("") }//mô tả công việc
    var requirement by remember { mutableStateOf("") }//yêu cầu
    var benefit by remember { mutableStateOf("") }//quyền lợi cho người tìm việc
    var address by remember { mutableStateOf("") }//vị trí
    var quantity by remember { mutableStateOf("") } //số lượng tuyển

    //working time
    var startDate by remember { mutableStateOf("start") }
    var endDate by remember { mutableStateOf("end") }

    var deadline by remember { mutableStateOf("dealine") } //hạn đăng bài

    var bottomSheetState = rememberModalBottomSheetState() //state cua bottom sheet
    var showBottomSheet by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
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
                    TextButton(onClick = {
                        showBottomSheet = true
                    }) {
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

            LabeledInput("Hình thức làm việc", address) { address = it }
            LabeledInput("Số lượng tuyển", quantity) { quantity = it }

            //DatePickerRangeInput cho thời gian làm việc
            OutlinedTextField(
                value = startDate + " - " + endDate,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable() {
                        showDatePickerRange = true
                    },
                readOnly = true,
                enabled = false,
                trailingIcon = {
                    Icon(Icons.Default.CalendarToday, contentDescription = "DatePicker + Icon")
                },
                label = { Text("Thời gian làm việc") }

            )

            Spacer(Modifier.height(8.dp))

            //DatePickerInput cho hạn đăng tuyển
            OutlinedTextField(
                value = deadline,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable() {
                        showDatePicker = true
                    },
                readOnly = true,
                enabled = false,
                trailingIcon = {
                    Icon(Icons.Default.CalendarToday, contentDescription = "DatePicker + Icon")
                },
                label = { Text("Hạn đăng tuyển") }

            )
            Spacer(Modifier.height(32.dp))

            //xu ly logic de hien thi dialog datepicker
            if (showDatePicker) {
                DatePickerInput(
                    onDateSelected = { millis ->
                        millis?.let {
                            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            deadline = formatter.format(it)
                        }
                    },
                    onDismiss = { showDatePicker = false },
                )
            }

            if (showDatePickerRange) {
                DatePickerRangeInput(
                    onDateRangeSelected = { range ->
                        range.first?.let {
                            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            startDate = formatter.format(it)
                        }
                        range.second?.let {
                            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            endDate = formatter.format(it)
                        }
                    },
                    onDismiss = { showDatePickerRange = false }
                )
            }

            //xu ly logic de hien bottom sheet = click button "Tao"
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = bottomSheetState
                ) {
                    AddBottomSheetContent(onApply = {
                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun AddBottomSheetContent(
    onApply: () -> Unit //chuyen doi state
) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        Text(
            "Tạo công việc mới", style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Hãy chắc chắn tạo công việc này",
            style = TextStyle(
                color = Color.Gray,
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(24.dp))
        Button(onClick = {
            //todo add new job
        }, modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .width(250.dp)) {
            Text("Tạo ngay")
        }
        Button(
            onClick =
                onApply,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(250.dp)
        ) {
            Text("Kiểm tra lại")
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
) {
    var datePickerState = rememberDatePickerState() //state của datepicker

    androidx.compose.material3.DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                //goi call back
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Huỷ")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerRangeInput(
    onDateRangeSelected: (Pair<Long?, Long?>) -> Unit,
    onDismiss: () -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onDateRangeSelected(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                    onDismiss()
                }
            ) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Huỷ")
            }
        }
    ) {
        DateRangePicker(
            state = dateRangePickerState,
            title = {
                Text(
                    text = "Chọn khoảng thời gian",
                    modifier = Modifier.padding(16.dp),
                )
            },
            showModeToggle = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(4.dp)
        )
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
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
//            placeholder = { Text("Write the title of your post here") }
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

