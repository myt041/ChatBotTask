package com.mayur.chatbottask.ui.chat

import com.google.gson.annotations.SerializedName

data class chatBotResponse(

   @SerializedName("success") var success: Int,
   @SerializedName("errorMessage") var errorMessage: String,
   @SerializedName("message") var message: Message,
   @SerializedName("data") var data: List<String>

)

data class Message(

   @SerializedName("chatBotName") var chatBotName: String,
   @SerializedName("chatBotID") var chatBotID: Int,
   @SerializedName("message") var message: String,
   @SerializedName("emotion") var emotion: String

)