package com.mayur.chatbottask.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MessageDao {

    @Insert
    suspend fun saveMessage(message: MessageCache)

    @Query("Select * from messages")
    fun getAllMessage(): List<MessageCache>

    @Query("SELECT * FROM messages WHERE userId=:userId ORDER BY timestamp DESC")
    fun getPreviousMessages(userId: String): List<MessageCache>

    @Query("SELECT * FROM messages WHERE isSynced=:isSynced")
    fun getUnSyncMessages(isSynced: Boolean): List<MessageCache>


}