package com.example.jobfinder.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jobfinder.navigation.AppRoutes
import com.example.jobfinder.utils.MyColorUtils

@Composable
fun PostCard(navController: NavController) {
    Card (
        modifier = Modifier.padding(12.dp).clickable(onClick = {
            navController.navigate(AppRoutes.JOB_DETAIL)
        }),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                Text("BD: 15/02/2025", fontSize = 12.sp,)
                Text("Con lai 3 ngay")
            }

            Spacer(modifier = Modifier.height(6.dp))

            //Job title
            Text(text = "Tuyển nhân viên bán hàng", fontSize = 16.sp, fontWeight = FontWeight.Bold)


            //date
            Card (modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp).border(1.dp, MyColorUtils.Grey200, RoundedCornerShape(10.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = MyColorUtils.Grey100,

                ),
            ){
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)){
                    Column (modifier = Modifier
                        .padding(8.dp)) {
                        Text("Tong thoi gian", fontSize = 12.sp,)
                        Text("08:00:00 hrs", fontSize = 16.sp,)
                    }
                    Column(modifier = Modifier
                        .padding(8.dp)) {
                        Text("Bat dau - Ket thuc", fontSize = 12.sp,)
                        Text("09:00 AM - 05:00 PM ", fontSize = 16.sp,)
                    }
                }
            }

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween){
                Text("Ung vien: 20", fontSize = 12.sp)
                Text("SL: 3", fontSize = 12.sp)
            }
        }
    }
}
