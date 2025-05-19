package com.example.jobfinder.presentation.workspace.create

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jobfinder.data.local.UserSessionManager
import com.example.jobfinder.utils.Utils
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateJobPage(navController: NavController, createJobViewModel: CreateJobViewModel) {
    val context = LocalContext.current
    val stateCreateJob by createJobViewModel.stateCreateJob.collectAsState()

    val scrollState = rememberScrollState()

    var showDatePicker_endAt by remember { mutableStateOf(false) } // show dialog chọn deadline
    var showDatePicker_workingAt by remember { mutableStateOf(false) } // show dialog chọn workingAt
    var showStartTimePicker by remember { mutableStateOf(false) } // show dialog chọn workingAt
    var showEndTimePicker by remember { mutableStateOf(false) } // show dialog chọn workingAt

    var formattedDate_workingAt by remember { mutableStateOf("") }
    var formattedDate_endAt by remember { mutableStateOf("") } //deadline
    var formattedDate_startTime by remember { mutableStateOf("") } //bắt đầu ca
    var formattedDate_endTime by remember { mutableStateOf("") } //kết thúc ca


    var bottomSheetState = rememberModalBottomSheetState() //state cua bottom sheet
    var showBottomSheet by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = stateCreateJob.isSuccess, key2 = stateCreateJob.errorMessage) {
        if (stateCreateJob.isSuccess) {
            navController.popBackStack()

            //reset state
            createJobViewModel.onCreateEvent(CreateJobEvent.ResetState)
        }

        stateCreateJob.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Việc làm mới", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
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
            //handle Event
            //xu ly logic de hien thi dialog datepicker
            if (showDatePicker_endAt) {
                DatePickerInput(
                    onDateSelected = { millis ->
                        millis?.let {
                            // 1. Format ngày cho giao diện người dùng
                            val userDateFormatter =
                                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            formattedDate_endAt = userDateFormatter.format(it)

                            // 2. Chuyển millis -> LocalDateTime, rồi set giờ = 23:59:59
                            val zoneId = ZoneId.systemDefault()
                            val selectedDateTime = Instant.ofEpochMilli(it)
                                .atZone(zoneId)
                                .toLocalDate()
                                .atTime(23, 59, 59)

                            // 3. Format theo định dạng ISO 8601 cho server
                            val serverDateFormatter =
                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                            val formattedServerDate = serverDateFormatter.format(selectedDateTime)

                            // 4. Gửi event lên ViewModel
                            createJobViewModel.onCreateEvent(
                                CreateJobEvent.EndAtChanged(formattedServerDate)
                            )
                            Log.d("        ", "user_endAt: $formattedDate_endAt")
                            Log.d("Create Job Page", "server_endAt: $formattedServerDate")
                        }
                    },
                    onDismiss = { showDatePicker_endAt = false },
                )
            }

            if (showDatePicker_workingAt) {
                DatePickerInput(
                    onDateSelected = { millis ->
                        millis?.let {
                            // 1. Format ngày cho giao diện người dùng
                            val userDateFormatter =
                                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            formattedDate_workingAt = userDateFormatter.format(it)

                            // 2. Chuyển millis -> LocalDateTime, rồi set giờ = 23:59:59
                            val zoneId = ZoneId.systemDefault()
                            val selectedDateTime = Instant.ofEpochMilli(it)
                                .atZone(zoneId)
                                .toLocalDate()
                                .atTime(23, 59, 59)

                            // 3. Format theo định dạng ISO 8601 cho server
                            val serverDateFormatter =
                                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                            val formattedServerDate = serverDateFormatter.format(selectedDateTime)

                            // 4. Gửi event lên ViewModel
                            createJobViewModel.onCreateEvent(
                                CreateJobEvent.WorkingAtChanged(formattedServerDate)
                            )

                            Log.d("Create Job Page", "user_workingAt: $formattedDate_workingAt")
                            Log.d("Create Job Page", "server_workingAt: $formattedServerDate")
                        }
                    },
                    onDismiss = { showDatePicker_workingAt = false },
                )
            }

            if (showStartTimePicker) {
                TimePickerDialog(
                    onDismissRequest = { showStartTimePicker = false },
                    onTimeSelected = { hour, minute ->
//                        val formattedTime = formatTime(hour, minute)
//                        createJobViewModel.onCreateEvent(
//                            CreateJobEvent.StartTimeChanged(
//                                formattedTime
//                            )
//                        )
                        //0. Hiển thị format cho người dùng
                        formattedDate_startTime = formatTime(hour, minute)

                        // 1. Tạo LocalDateTime = ngày + giờ chọn
                        var selectedDate: LocalDate =
                            Utils.stringToLocalDateTime(stateCreateJob.workingAt)!!.toLocalDate()

                        val startDateTime = selectedDate.atTime(hour, minute, 0)


                        // 2. Format theo chuẩn ISO 8601 gửi lên server
                        val serverFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                        val formattedServerTime = serverFormatter.format(startDateTime)

                        // 3. Gửi lên ViewModel
                        createJobViewModel.onCreateEvent(
                            CreateJobEvent.StartTimeChanged(formattedServerTime)
                        )

                        Log.d("Create Job Page", "user_startTime: $formattedDate_startTime")
                        Log.d("Create Job Page", "server_startTime: $formattedServerTime")
                    }
                )
            }

            if (showEndTimePicker) {
                TimePickerDialog(
                    onDismissRequest = { showEndTimePicker = false },
                    onTimeSelected = { hour, minute ->
                        //0. Hiển thị format cho người dùng
                        formattedDate_endTime = formatTime(hour, minute)

                        // 1. Tạo LocalDateTime = ngày + giờ chọn
                        var selectedDate: LocalDate =
                            Utils.stringToLocalDateTime(stateCreateJob.workingAt)!!.toLocalDate()

                        val startDateTime = selectedDate.atTime(hour, minute, 0)


                        // 2. Format theo chuẩn ISO 8601 gửi lên server
                        val serverFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                        val formattedServerTime = serverFormatter.format(startDateTime)

                        // 3. Gửi lên ViewModel
                        createJobViewModel.onCreateEvent(
                            CreateJobEvent.EndTimeChanged(formattedServerTime)
                        )

                        Log.d("Create Job Page", "user_endTime: $formattedDate_endTime")
                        Log.d("Create Job Page", "server_endTime: $formattedServerTime")
                    }
                )
            }

            //xu ly logic de hien bottom sheet = click button "Tao"
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false },
                    sheetState = bottomSheetState
                ) {
                    AddBottomSheetContent(
                        onCancel = {
                            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        },
                        onApply = {
                            scope.launch {
                                createJobViewModel.onCreateEvent(CreateJobEvent.SendRequest)
                                bottomSheetState.hide()
                                showBottomSheet = false
                            }
                        })
                }
            }

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
                    Text(UserSessionManager.getUserName(), fontWeight = FontWeight.Bold)
                    Text("Đang tuyển dụng tại Hà Nội", fontSize = 12.sp, color = Color.Gray)
                }
            }

            Spacer(Modifier.height(16.dp))

            /******
             * TITLE
             * ********/
            LabeledInput("Tiêu đề", stateCreateJob.title) {
                createJobViewModel.onCreateEvent(
                    CreateJobEvent.TitleChanged(it)
                )
            }
            /******
             * DESCRIPTION
             * ********/
            LabeledInput("Mô tả", stateCreateJob.description, singleLine = false) {
                createJobViewModel.onCreateEvent(
                    CreateJobEvent.DescriptionChanged(it)
                )
            }

            /******
             * REQUIREMENT
             * ********/
            LabeledInput("Yêu cầu ứng viên", stateCreateJob.requirement, singleLine = false) {
                createJobViewModel.onCreateEvent(
                    CreateJobEvent.RequirementChanged(it)
                )
            }

            /******
             * BENEFIT
             * ********/
            LabeledInput("Quyền lợi", stateCreateJob.benefit, singleLine = false) {
                createJobViewModel.onCreateEvent(
                    CreateJobEvent.BenefitChanged(it)
                )
            }

            /******
             * LOCATION
             * ********/
            LabeledInput("Địa điểm làm việc", stateCreateJob.location) {
                createJobViewModel.onCreateEvent(
                    CreateJobEvent.LocationChanged(it)
                )
            }

            /******
             * QUANTITY
             * ********/
            LabeledInput(
                "Số lượng tuyển",
                value = stateCreateJob.numberOfPositions
            ) {
                createJobViewModel.onCreateEvent(
                    CreateJobEvent.NumberOfPositionsChanged(it)
                )
            }

            /******
             * TIME PICKER: DEADLINE
             * ********/
            DateField(
                label = "Hạn đăng tuyển",
                value = formattedDate_endAt,
                onPickerRequested = { showDatePicker_endAt = true })

            /******
             * DATE PICKER: WORKING
             * ********/
            DateField(
                label = "Ngày bắt đầu làm việc",
                value = formattedDate_workingAt,
                onPickerRequested = { showDatePicker_workingAt = true }
            )

            if (stateCreateJob.workingAt.isNotEmpty()) {
                /******
                 * TIME PICKER: START
                 * ********/
                TimePickerField(
                    label = "Giờ bắt đầu",
                    value = formattedDate_startTime,
                    onTimePickerRequested = { showStartTimePicker = true }
                )

                /******
                 * TIME PICKER: END
                 * ********/
                TimePickerField(
                    label = "Giờ kết thúc",
                    value = formattedDate_endTime,
                    onTimePickerRequested = { showEndTimePicker = true }
                )
            }



            Spacer(Modifier.height(32.dp))

        }
    }
}

@Composable
fun AddBottomSheetContent(
    onCancel: () -> Unit, //chuyen doi state
    onApply: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
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
        Button(
            onClick = onApply, modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(250.dp)
        ) {
            Text("Tạo ngay")
        }
        Button(
            onClick =
                onCancel,
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

    DatePickerDialog(
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
fun DateField(label: String, value: String, onPickerRequested: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(label, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = value,
            onValueChange = { /* Do nothing, it's read-only */ },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
//            interactionSource = interactionSource,
            enabled = false,
            trailingIcon = {
                Icon(
                    Icons.Default.CalendarToday, contentDescription = "DatePicker",
                    modifier = Modifier.clickable {
                        onPickerRequested()
                    })
            },
//            label = { Text(label) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimeSelected: (Int, Int) -> Unit,
    initialHour: Int = 9,
    initialMinute: Int = 0,
) {


    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute
    )

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                onTimeSelected(timePickerState.hour, timePickerState.minute)
                onDismissRequest()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Huỷ")
            }
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TimePicker(state = timePickerState)
            }
        }
    )
}

@Composable
fun TimePickerField(
    label: String,
    value: String,
    onTimePickerRequested: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(label, fontWeight = FontWeight.SemiBold)
        OutlinedTextField(
            value = value,
            onValueChange = { /* Read-only field */ },
            readOnly = true,
            enabled = false,
            modifier = Modifier.fillMaxWidth(),
//            interactionSource = interactionSource,
            trailingIcon = {
                Icon(
                    Icons.Default.AccessTime,
                    contentDescription = "Time Picker",
                    modifier = Modifier.clickable { onTimePickerRequested() }
                )
            },
//            label = { Text(label) }
        )
    }
}

// Helper function to format time
@SuppressLint("DefaultLocale")
fun formatTime(hour: Int, minute: Int): String {
    return String.format("%02d:%02d", hour, minute)
}
