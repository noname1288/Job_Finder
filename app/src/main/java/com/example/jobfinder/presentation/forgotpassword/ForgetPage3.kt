package com.example.jobfinder.presentation.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jobfinder.R
import com.example.jobfinder.navigation.AppRoutes

@Composable
fun ForgetPage3(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(85.dp))

        Text(
            text = "Đổi mật khẩu \n" +
                    "thành công", fontSize = 36.sp, fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(16.dp))


        Spacer(Modifier.height(85.dp))

        //Image
        Image(
            painter = painterResource(id = R.drawable.img_person_emailtick),
            contentDescription = null,
            Modifier.size(width = 128.dp, height = 106.dp)
        )

        Spacer(Modifier.height(52.dp))


        //button area
        Spacer(Modifier.height(52.dp))

        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.navigate(AppRoutes.LOGIN) }) {
            Text("QUAY LẠI TRANG ĐĂNG NHẬP")
        }

    }
}
