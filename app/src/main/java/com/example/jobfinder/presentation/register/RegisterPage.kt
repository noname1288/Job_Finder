package com.example.jobfinder.presentation.register

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.SnackbarHostState
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
import com.example.jobfinder.presentation.RegisterEvent

@Composable
fun RegisterPage(navController: NavController, authViewModel: AuthViewModel) {
    val context = LocalContext.current

    val state by authViewModel.stateRegister.collectAsState()

    var passwordVisibility by remember { mutableStateOf(false) }

    //show success or error
    LaunchedEffect(key1 = state.isSuccess, key2 = state.errorMessage) {
        if (state.isSuccess) {
            Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
            //navigate to login page
            navController.navigate(AppRoutes.LOGIN)
            //reset state
            authViewModel.onNavigatedToRegister()

        }

        state.errorMessage?.let { error ->
            Toast.makeText(context, "Lỗi: $error", Toast.LENGTH_SHORT).show()
        }
    }

    /* -------------------- UI GỐC -------------------- */
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(65.dp))

        Text("JobSpot", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Đăng ký để trở thành nhà tuyển dụng",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic
        )

        Spacer(Modifier.height(65.dp))

        /* Input Area */
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(36.dp)
        ) {
            /* Full Name Field */
            OutlinedTextField(
                value = state.fullName,
                onValueChange = { authViewModel.onRegisterEvent(RegisterEvent.FullNameChanged(it)) },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            /* Email */
            OutlinedTextField(
                value = state.email,
                onValueChange = { authViewModel.onRegisterEvent(RegisterEvent.EmailChanged(it)) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email, //define keyboard
                    imeAction = ImeAction.Next //IME = (Input Method Editor) ~ show Enter or Return
                )
            )

            Spacer(Modifier.height(16.dp))

            /* Password */
            OutlinedTextField(
                value = state.password,
                onValueChange = { authViewModel.onRegisterEvent(RegisterEvent.PasswordChanged(it)) },
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

        }
        Button(
            onClick = { authViewModel.onRegisterEvent(RegisterEvent.Register) },
            enabled = !state.isLoading &&
                    state.fullName.isNotBlank() &&
                    state.password.isNotBlank() &&
                    state.email.isNotBlank()
        ) {
            if (state.isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.padding(end = 8.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            }
            Text("Đăng ký")
        }
        Spacer(Modifier.height(8.dp))
        Text("Đã có tài khoản, quay lại trang đăng nhập", style = TextStyle(
            fontStyle = FontStyle.Italic,
        ), modifier = Modifier.clickable{
            navController.safeNavigate(AppRoutes.LOGIN)
        })


    }
}