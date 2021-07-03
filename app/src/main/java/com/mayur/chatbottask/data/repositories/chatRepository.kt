package com.mayur.chatbottask.data.repositories

import com.mayur.chatbottask.data.network.ApiServices
import com.mayur.chatbottask.ui.chat.chatBotResponse
import com.mayur.chatbottask.util.SafeApiRequest

class ChatRepository(
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
}