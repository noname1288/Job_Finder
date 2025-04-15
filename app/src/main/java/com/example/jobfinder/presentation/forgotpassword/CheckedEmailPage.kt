package com.example.jobfinder.presentation.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jobfinder.R


@Composable
fun CheckedEmailPage() {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(85.dp))

        Text(
            text = "Kiểm tra email \n" +
                    "của bạn", fontSize = 36.sp, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Chúng tôi đã gửi yêu cầu đặt lại mật khẩu đến địa chỉ email brandonelouis@gmial.com.",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp)
        )

        Spacer(Modifier.height(85.dp))

        //Image
        Image(
            painter = painterResource(id = R.drawable.img_person_openemail),
            contentDescription = null,
            Modifier.size(width = 128.dp, height = 106.dp)
        )

        Spacer(Modifier.height(52.dp))

        //button area
        Spacer(Modifier.height(16.dp))
        Button(onClick = {}) {
            Text("Quay lại")
        }

        //gui lai
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Bạn chưa nhận được email?")
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Gửi lại",
                color = if (isPressed) {
                    Color.Red
                } else {
                    Color.Black
                },
                fontStyle = FontStyle.Italic,
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    println("Text đã được nhấn!")
                }.padding(16.dp)
            )

        }


    }
}

@Preview(showBackground = true)
@Composable
fun CheckedEmailPageDemo() {
    CheckedEmailPage()
}