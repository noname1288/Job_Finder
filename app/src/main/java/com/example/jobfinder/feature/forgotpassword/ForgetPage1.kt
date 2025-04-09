package com.example.jobfinder.feature.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jobfinder.R
import com.example.jobfinder.navigation.AppRoutes

@Composable
fun ForgetPage1(navController: NavController) {

    var forgetEmail by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(85.dp))

        Text(text = "Quên mật khẩu", fontSize = 36.sp, fontWeight = FontWeight.Bold)

        Spacer( Modifier.height(16.dp))

        Text(
            text = "Để đặt lại mật khẩu, bạn cần có email hoặc số điện thoại di động có thể xác thực.",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp)
        )

        Spacer(Modifier.height(85.dp))

        //Image
        Image(painter = painterResource(id = R.drawable.img_person_key), contentDescription = null,
            Modifier.size(width = 128.dp, height = 106.dp))

        Spacer(Modifier.height(52.dp))
        //email
        OutlinedTextField(
            value =forgetEmail,
            onValueChange = {forgetEmail = it},
            label = {Text("Email")}
        )

        //button area
        Spacer(Modifier.height(52.dp))
        Button(onClick = {navController.navigate(AppRoutes.FORGOT_PASSWORD2)}){
            Text("Đặt lại mật khẩu")
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = {navController.popBackStack()}){
            Text("Quay lại")
        }

    }
}
