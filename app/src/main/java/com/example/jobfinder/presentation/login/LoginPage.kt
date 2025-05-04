package com.example.jobfinder.presentation.login

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.jobfinder.core.NetworkResult
import com.example.jobfinder.navigation.AppRoutes
import com.example.jobfinder.navigation.safeNavigate
import com.example.jobfinder.service_locator.AppContainer
import kotlinx.coroutines.flow.collectLatest

/* ------------------------------------------------------------------ */
/*  ViewModel khởi tạo bằng Factory – vẫn giữ toàn bộ layout cũ        */
/* ------------------------------------------------------------------ */

@Composable
fun LoginPage(navController: NavController) {

    /* Lấy đúng instance bằng Factory (không truyền qua tham số nữa) */
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(AppContainer.loginUseCase)
    )

    val context = LocalContext.current

    /* State cũ */
    val emailInput by loginViewModel.username.observeAsState("")
    val passwordInput by loginViewModel.password.observeAsState("")
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }
    var showLoading by remember { mutableStateOf(false) }
    var emailError = remember { mutableStateOf<String?>(null) }

    /* Lắng nghe kết quả login để điều hướng */
    LaunchedEffect(Unit) {
        loginViewModel.loginState.collectLatest { state ->
            when (state) {
                is NetworkResult.Loading -> showLoading = true
                is NetworkResult.Success -> {
                    showLoading = false
                    /* Đăng nhập thành công → về HOME và xoá Login khỏi back-stack */
                    navController.safeNavigate(
                        route = AppRoutes.HOME,
                        popUpToRoute = AppRoutes.LOGIN,
                        isInclusive = true
                    )
                }

                is NetworkResult.Error -> {
                    showLoading = false
                    state.message?.let { msg ->
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        println("Login error: $msg")
                    }
                }

                else -> Unit
            }
            Log.i("LoginPageChange", "loginState: $state")
        }
    }

    /*------------------handle loading----------------------*/
    if (showLoading) {
        LoadingDialog()  // Hoặc LoadingIndicator()
    }

    /* -------------------- UI GỐC -------------------- */
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(85.dp))

        Text("JobSpot", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Chào mừng bạn với tư cách nhà tuyển dụng",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic
        )

        Spacer(Modifier.height(85.dp))

        /* Input Area */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(36.dp)
        ) {

            /* Email */
            OutlinedTextField(
                value = emailInput,
                onValueChange = { input ->
                    loginViewModel.updateNameChange(input)
                    emailError.value = if (isValidEmail(input)) null else "Email không hợp lệ"
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            /* Password */
            OutlinedTextField(
                value = passwordInput,
                onValueChange = { loginViewModel.updatePasswordChange(it) },
                label = { Text("Password") },
                visualTransformation = if (!passwordVisibility)
                    PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val icon = if (passwordVisibility) Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(icon, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(24.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Ghi nhớ mật khẩu")
                Text(
                    "Quên mật khẩu",
                    modifier = Modifier.clickable {
                        navController.safeNavigate(AppRoutes.FORGOT_PASSWORD1)
                    }
                )
            }
        }

        /* Button area – gọi login() */
        ButtonArea(
            navController,
            loginViewModel,
            email = emailInput,
            emailError = emailError.value,
            context = context
        )

        Spacer(Modifier.height(16.dp))

        Text("Đăng ký")
    }
}

/* --------------------------------------------------- */
/* ButtonArea giữ UI – chỉ thêm gọi login()            */
/* --------------------------------------------------- */
@Composable
fun ButtonArea(
    navController: NavController,
    loginViewModel: LoginViewModel,
    email: String,
    emailError: String?,
    context: Context
) {
    Button(
        onClick = {
            if (!isValidEmail(email)) {
                Toast.makeText(context, emailError, Toast.LENGTH_SHORT).show()
            } else {
                loginViewModel.login()
            }
            Log.i("LoginPage", "clicked")
        }
    ) {
        Text("Đăng nhập")
    }
}

/* --------------------------------------------------- */
/* Loading Indicator          */
/* --------------------------------------------------- */
@Composable
fun LoadingDialog() {
    Dialog(onDismissRequest = { /* Không cho dismiss */ }) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

//function validate email
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
