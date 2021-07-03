package com.mayur.chatbottask.ui.chat

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChannelPojo(
    var chatId: String,
    var lastMessage: String,
    var lastMessageDate: String,
    var messageType: Int,
    var messageUnreadCount: Int,
    var objectId: String,
    var receiverId: String,
    var senderId: String,
    var receiverName: String,
    var receiverProfile: String,
    var receiverBussinessName: String,
    var receiverBussinessImage: String
) : Parcelable