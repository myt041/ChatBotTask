package com.mayur.chatbottask.data.network

import com.mayur.chatbottask.data.network.response.chatBotResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("api/chat/")
    suspend fun getBotReply(
        @Query("apiKey") apiKey: String,
        @Query("message") message: String,
        @Query("chatBotID") chatBotID: String,
        @Query("externalID") externalID: String,
    ): Response<chatBotResponse>

}