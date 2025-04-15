package com.example.jobfinder.presentation.login

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable

import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jobfinder.navigation.AppRoutes


@Composable
fun LoginPage(navController: NavController, loginViewModel: LoginViewModel) {
//    var passwordInput by rememberSaveable { mutableStateOf("") }
//    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    val emailInput by loginViewModel.username.observeAsState(initial = "")
    val passwordInput by loginViewModel.password.observeAsState(initial = "")
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(85.dp))

        Text(text = "JobSpot", fontSize = 36.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Chào mừng bạn với tư cách nhà tuyển dụng",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic
        )

        Spacer(Modifier.height(85.dp))

        //Input Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(36.dp)
        ) {
            /*Email input*/
            OutlinedTextField(
                value = emailInput,
                onValueChange = { newEmail -> loginViewModel.updateNameChange(newEmail) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            /*Password input*/
            OutlinedTextField(
                value = passwordInput,
                onValueChange = { newPassword -> loginViewModel.updatePasswordChange(newPassword) },
                label = { Text("Password") },
                visualTransformation = if (passwordVisibility) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val icon = if (passwordVisibility) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }
                    IconButton(onClick = {/*todo*/ }) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Ghi nhớ mật khẩu")
                Text(text = "Quên mật khẩu", modifier = Modifier.clickable(onClick = {
                    navController.navigate(AppRoutes.FORGOT_PASSWORD1)
                }))
            }
        }

        //button area
        ButtonArea(navController)

        Spacer(Modifier.height(16.dp))

        //Register
        Text(text = "Đăng ký")

    }
}

@Composable
fun ButtonArea(navController : NavController) {
    Button(onClick = {
        navController.navigate("home")
    }) {
        Text(text = "Đăng nhập")
    }
}

