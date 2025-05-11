package com.example.jobfinder.presentation.message

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.jobfinder.data.ChatItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagePage(
    chatViewModel: ChatViewModel,
    navController: NavController,
    onChatClicked: (ChatItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val chatList by chatViewModel.chatList.observeAsState(emptyList())

    Scaffold(
        topBar = {
            // Top bar custom: Nút back + Tiêu đề
            TopAppBar(
                title = { Text(text = "Message") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier.padding(innerPadding),
            contentPadding = PaddingValues(top = 24.dp)
        ) {
            items(chatList) { chat ->
                ChatRow(
                    item = chat,
                    onClick = { onChatClicked(chat) }
                )
                HorizontalDivider(color = Color.LightGray, thickness = 0.5.dp)
            }
        }
    }


}

@Composable
fun ChatRow(item: ChatItem, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1) Avatar
        AvatarSection(item.avatar, item.name)
        // 2) Tên + LastMessage
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = item.lastMessage,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    fontSize = 14.sp
                ),
                maxLines = 1
            )
        }
        // 3) Thời gian + Badge
        Column(horizontalAlignment = Alignment.End) {
            Text(item.time, style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
            Spacer(modifier = Modifier.height(4.dp))
            if (item.unreadCount > 0) {
                BadgeUnread(count = item.unreadCount)
            }
        }
    }
}

@Composable
fun AvatarSection(avatarUrl: String?, name: String) {
    val avatarSize = 48.dp
    if (avatarUrl.isNullOrBlank()) {
        // Giả lập khi chưa có ảnh => hiển thị 2 ký tự đầu
        Box(
            modifier = Modifier
                .size(avatarSize)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(name.take(2))
        }
    } else {
        Image(
            painter = rememberAsyncImagePainter(avatarUrl),
            contentDescription = null,
            modifier = Modifier
                .size(avatarSize)
                .clip(CircleShape)
        )
    }
}

@Composable
fun BadgeUnread(count: Int) {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary
    ) {
        Text(
            text = if (count > 999) "999+" else count.toString(),
            color = Color.White,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium)
        )
    }
}
