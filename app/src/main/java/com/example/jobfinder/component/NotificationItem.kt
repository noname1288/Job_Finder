package com.example.jobfinder.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jobfinder.R

@Composable
fun NotificationItem(modifier: Modifier = Modifier) {
    Row(Modifier.padding(start = 12.dp, end = 12.dp, top = 24.dp, bottom = 24.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painterResource(R.drawable.avt), contentDescription = null,
            modifier = Modifier.size(64.dp).clip(RoundedCornerShape(10.dp)).padding(start = 8.dp)
        )

        Spacer(Modifier.width(12.dp))

        Column {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Bạn có 1 tin nhắn mới", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "09.10", fontSize = 12.sp)
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Nội dung tin nhắn rovides a straightforward way to create an assist chip " +
                        "that nudges the user in a particular direction. One distinguishing feature is " +
                        "its leadingIcon parameter that lets you display an icon on the left side of the chip. The following example demonstrates how you can implement it:",
                fontSize = 12.sp,
                maxLines = 2,
                style = TextStyle(lineHeight = 16.sp)
            )
        }
    }
}

@Preview
@Composable
fun NotificationItemPreview() {
    NotificationItem()
}