package com.example.jobfinder.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jobfinder.navigation.AppRoutes

@Composable
fun ProfilePage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(innerPaddingValues)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "My Profile",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        // Avatar + Tên
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = -60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                shape = CircleShape,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 4.dp
            ) {
                // Nơi để ảnh avatar (đoạn demo text “Avatar”)
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "Avatar",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text("Tonald Drump", style = MaterialTheme.typography.titleLarge)
            Text("ABC XYZ Company", style = MaterialTheme.typography.bodyMedium)
        }

//        Spacer(Modifier.height(8.dp))

        // Card: Liên hệ
        ProfileSectionCard(
            title = "Liên hệ"
        ) {
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                headlineContent = {
                    Text("Tonald@gmail.com")
                }
            )
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.Info, contentDescription = null)
                },
                headlineContent = {
                    Text("Hello World")
                }
            )
        }

        // Card: Tài khoản
        ProfileSectionCard(
            title = "Tài khoản"
        ) {
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.AccountBox, contentDescription = null)
                },
                headlineContent = {
                    Text("Thông tin")
                }
            )
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.List, contentDescription = null)
                },
                headlineContent = {
                    Text("Tất cả bài đăng")
                }
            )
        }

        // Card: Cài đặt
        ProfileSectionCard(
            title = "Cài đặt"
        ) {
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.Lock, contentDescription = null)
                },
                headlineContent = {
                    Text("Thay đổi mật khẩu")
                }
            )
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.Build, contentDescription = null)
                },
                headlineContent = {
                    Text("Phiên bản")
                }
            )
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.Help, contentDescription = null)
                },
                headlineContent = {
                    Text("Hỗ trợ")
                }
            )
            ListItem(
                leadingContent = {
                    Icon(Icons.Default.ExitToApp, contentDescription = null)
                },
                headlineContent = {
                    Text("Đăng xuất")
                },
                modifier = Modifier.clickable(onClick = {
                    navController.navigate(AppRoutes.LOGIN){
                        popUpTo(AppRoutes.LOGIN){
                            inclusive = true
                        }
                    }
                })
            )
        }

        Spacer(Modifier.height(16.dp))
    }
}


/**
 * Mẫu Card hiển thị phần tiêu đề và nội dung ListItem
 */
@Composable
fun ProfileSectionCard(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
            content()
        }
    }
}
