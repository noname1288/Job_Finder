package com.example.jobfinder.model

/**
 * Mỗi phòng chat có ID, tên, avatar, tin nhắn cuối, etc.
 */
data class ChatItem(
    val id: String,           // ID duy nhất (UUID, v.v.)
    val avatar: String?,      // URL hoặc resource ID của ảnh đại diện
    val name: String,         // Tên nhóm/người
    val lastMessage: String,  // Đoạn tin nhắn cuối
    val time: String,         // Thời gian (VD: "01:46")
    val unreadCount: Int      // Số tin chưa đọc
)

/**
 * Dữ liệu mô phỏng
 */
val mockChats = listOf(
    ChatItem(
        id = "1",
        avatar = null,
        name = "Làng Nghề",
        lastMessage = "Bird: Bóc cứt đó, giỡn hoài",
        time = "01:46",
        unreadCount = 48075
    ),
    ChatItem(
        id = "2",
        avatar = null,
        name = "Em Dangyeunhat",
        lastMessage = "Love diuuuu",
        time = "00:46",
        unreadCount = 0
    ),
    ChatItem(
        id = "3",
        avatar = null,
        name = "Group",
        lastMessage = "Plan giao dịch #General - ...",
        time = "18:15",
        unreadCount = 4
    ),
    ChatItem(
        id = "4",
        avatar = null,
        name = "ACE Ptit ❤️",
        lastMessage = "NBB: @ares18d a ơi",
        time = "Wed",
        unreadCount = 1
    ),
    ChatItem(
        id = "5",
        avatar = null,
        name = "EMILY Đang cười",
        lastMessage = "GM các thầy 🤯",
        time = "Wed",
        unreadCount = 97
    ),
    ChatItem(
        id = "6",
        avatar = null,
        name = "Kotlin Channel",
        lastMessage = "đang làm lòi ra rồi...",
        time = "Tue",
        unreadCount = 3
    )
)
