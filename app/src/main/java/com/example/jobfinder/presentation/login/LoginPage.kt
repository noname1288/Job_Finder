package com.example.jobfinder.presentation.login

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jobfinder.navigation.AppRoutes
import com.example.jobfinder.navigation.safeNavigate
import com.example.jobfinder.presentation.AuthViewModel
import com.example.jobfinder.presentation.LoginEvent

@Composable
fun LoginPage(modifier: Modifier,navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current

    val state by authViewModel.stateLogin.collectAsState()
//    val snackbarHostState = remember { SnackbarHostState() }
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = state.isSuccess, key2 = state.errorMessage) {
        if (state.isSuccess) {
            // Show success message first
//            snackbarHostState.showSnackbar("Login successful!")
            Toast.makeText(context, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show()
            // Then navigate
            navController.safeNavigate(AppRoutes.HOME, isInclusive = true)
            // Reset state
            authViewModel.onNavigatedToHome()
        }

        state.errorMessage?.let { error ->
            Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
        }
    }

    // Wrap everything in a Scaffold to properly display snackbars

    /* -------------------- UI GỐC -------------------- */
    Column(
        modifier = modifier.fillMaxSize(), // Apply padding from Scaffold
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(60.dp))

        Text("JobSpot", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Chào mừng bạn với tư cách nhà tuyển dụng",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic
        )

        Spacer(Modifier.height(60.dp))

        /* Input Area */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(36.dp)
        ) {
            /* Email */
            OutlinedTextField(
                value = state.username,
                onValueChange = { authViewModel.onLoginEvent(LoginEvent.EmailChanged(it)) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            Spacer(Modifier.height(16.dp))

            /* Password */
            OutlinedTextField(
                value = state.password,
                onValueChange = { authViewModel.onLoginEvent(LoginEvent.PasswordChanged(it)) },
                label = { Text("Password") },
                visualTransformation = if (!passwordVisibility)
                    PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
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
                Text(" ")
                Text(
                    "Quên mật khẩu",
                    modifier = Modifier.clickable {
                        navController.safeNavigate(AppRoutes.FORGOT_PASSWORD1)
                    }
                )
            }
        }

        Button(
            onClick = { authViewModel.onLoginEvent(LoginEvent.Login) },
            enabled = !state.isLoading &&
                    state.username.isNotBlank() &&
                    state.password.isNotBlank()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(end = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            }
            Text("Login")
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "Đăng ký ngay nếu bạn chưa có tài khoản",
            style = TextStyle(
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier.clickable {
                navController.safeNavigate(AppRoutes.REGISTER)
            }
        )
    }
}
