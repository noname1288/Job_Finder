package com.example.jobfinder.utils.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuItem(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Rounded.Person, contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .background(Color(0xFFF0F4FF), CircleShape)
                .padding(12.dp),
            tint = Color(0xFF6A56F7)
        )

//        IconButton(onClick = {}) {
//            Icon(
//                imageVector = Icons.Rounded.Person, contentDescription = null,
//                modifier = Modifier
//                    .size(80.dp)
//                    .background(Color(0xFFF0F4FF), CircleShape)
//                    .padding(8.dp),
//                tint = Color(0xFF6A56F7)
//            )
//        }
//        Spacer(modifier = Modifier.height(2.dp))
        Text("Ung vien", fontSize = 12.sp)

    }
}
