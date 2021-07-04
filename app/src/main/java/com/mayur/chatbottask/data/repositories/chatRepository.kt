package com.mayur.chatbottask.data.repositories

import com.mayur.chatbottask.data.cache.AppDatabase
import com.mayur.chatbottask.data.cache.MessageCache
import com.mayur.chatbottask.data.network.ApiServices
import com.mayur.chatbottask.ui.chat.User
import com.mayur.chatbottask.ui.chat.chatBotResponse
import com.mayur.chatbottask.util.SafeApiRequest

class ChatRepository(
    private val appDatabase: AppDatabase,
    private val apiService: ApiServices
) : SafeApiRequest() {

    val apiKey = "6nt5d1nJHkqbkphe"
    val chatBotID = "63906"

    suspend fun getBotReply(
        apiKey: String,
        message: String,
        chatBotID: String,
        externalID: String,
    ): chatBotResponse {
        return safeApiRequest { apiService.getBotReply(apiKey, message, chatBotID, externalID) }
    }

    fun getAllUsers(): ArrayList<User> {

        val userList = ArrayList<User>()

        userList.add(User("111", "Mayur", ""))
        userList.add(User("222", "User2", ""))
        userList.add(User("333", "User3", ""))
        userList.add(User("444", "User4", ""))
        userList.add(User("555", "User5", ""))

        return userList
    }

    suspend fun insertMessage(message: MessageCache) {
        appDatabase.messageDao().saveMessage(message)
    }

    suspend fun fetchPreviousMessage(userId: String): List<MessageCache> {
        return appDatabase.messageDao().getPreviousMessages(userId)
    }
}