package com.mayur.chatbottask.ui.model

import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.MessageContentType
import java.util.*

class ChatMessage @JvmOverloads constructor(
    private val user: User,
    private val message: String,
    private var messageDate: String,
    private var messageType: Int,
    private var objectId: String
) : IMessage, MessageContentType.Image, MessageContentType {

    override fun getId(): String {
        return objectId
    }

    override fun getText(): String {
        return message
    }

    override fun getCreatedAt(): Date {
        return Date(messageDate.toLong())
    }

    override fun getUser(): User {
        return user
    }

    override fun getImageUrl(): String? {
        return if (messageType == 0) null else message
    }


}