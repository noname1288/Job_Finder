package com.example.jobfinder.feature.message

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobfinder.data.entity.MessageData
import com.example.jobfinder.data.ChatItem
import com.example.jobfinder.data.mockChats

class ChatViewModel : ViewModel() {

    private val _chatList = MutableLiveData<List<ChatItem>>()
    val chatList: LiveData<List<ChatItem>> get() = _chatList

    private val _messages = MutableLiveData<List<MessageData>>()
    val messages: LiveData<List<MessageData>> get() = _messages

    init {
        // Load chat ban đầu
        _chatList.value = mockChats
    }

    /** Khi user chọn chat => mô phỏng load tin nhắn */
    fun loadMessagesForChat(chatId: String) {
        // Tuỳ ID -> ta fetch data. Ở đây “fake” cứng:
        val sample = listOf(
            MessageData("Alice", "Hello from chat #$chatId", "09:10", isMine = false),
            MessageData("Me", "Hi, how are you?", "09:12", isMine = true)
        )
        _messages.value = sample
    }

    fun sendMessage(newMessage: MessageData) {
        val current = _messages.value.orEmpty()
        _messages.value = current + newMessage
    }
}