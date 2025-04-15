package com.example.jobfinder.presentation.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jobfinder.R
import com.example.jobfinder.navigation.AppRoutes

@Composable
fun ForgetPage2(navController: NavController) {

    var forgetEmail by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(64.dp))

        Text(text = "Kiểm tra email \n" +
                "của bạn", fontSize = 36.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, style = TextStyle(lineHeight = 1.em))

        Spacer( Modifier.height(8.dp))

        Text(
            text = "Chúng tôi đã gửi yêu cầu đặt lại mật khẩu đến địa chỉ email brandonelouis@gmial.com.",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp)
        )

        Spacer(Modifier.height(64.dp))

        //Image
        Image(painter = painterResource(id = R.drawable.img_person_openemail), contentDescription = null,
            Modifier.size(width = 128.dp, height = 106.dp))


        //button area
        Spacer(Modifier.height(52.dp))
        Button(onClick = {navController.navigate(AppRoutes.FORGOT_PASSWORD3)}){
            Text("MỞ EMAIL CỦA BẠN")
        }
        Spacer(Modifier.height(16.dp))
        Button(onClick = {navController.navigate(AppRoutes.LOGIN)}){
            Text("QUAY LẠI TRANG ĐĂNG NHẬP")
        }
        Spacer(Modifier.height(16.dp))
        Text(text = "Gửi lại nếu bạn chưa nhận được email?", modifier = Modifier.clickable(onClick = {}))

    }
}
