package com.mayur.chatbottask.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
data class MessageCache(
    @PrimaryKey(autoGenerate = true) var messageId: Int,
    val userId: String,
    val sender_id: String,
    val receiver_id: String,
    val timestamp: Long,
    val userName: String,
    var contents: String,
    var isSynced: Boolean
)