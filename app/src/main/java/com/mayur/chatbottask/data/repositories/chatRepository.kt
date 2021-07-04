package com.mayur.chatbottask.data.repositories

import com.mayur.chatbottask.data.cache.AppDatabase
import com.mayur.chatbottask.data.cache.MessageCache
import com.mayur.chatbottask.data.network.ApiServices
import com.mayur.chatbottask.data.network.response.chatBotResponse
import com.mayur.chatbottask.util.SafeApiRequest
import com.rommansabbir.networkx.isInternetConnected

class ChatRepository(
    private val appDatabase: AppDatabase,
    private val apiService: ApiServices
) : SafeApiRequest() {


    suspend fun getBotReply(
        apiKey: String,
        message: String,
        chatBotID: String,
        externalID: String,
    ): chatBotResponse {
        return safeApiRequest { apiService.getBotReply(apiKey, message, chatBotID, externalID) }
    }


    suspend fun insertMessage(message: MessageCache) {
        if (isInternetConnected()) {
            message.isSynced = true
        }
        appDatabase.messageDao().saveMessage(message)
    }

    suspend fun fetchPreviousMessage(userId: String): List<MessageCache> {
        return appDatabase.messageDao().getPreviousMessages(userId)
    }

    suspend fun fetchUnAsyncMessages(): List<MessageCache> {
        return appDatabase.messageDao().getUnSyncMessages(false)
    }
}