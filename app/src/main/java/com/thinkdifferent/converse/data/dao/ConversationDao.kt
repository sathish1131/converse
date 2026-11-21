package com.thinkdifferent.converse.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.thinkdifferent.converse.data.entity.ConversationEntity
import com.thinkdifferent.converse.data.entity.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ConversationDao {
    @Insert
    suspend fun insertConversation(conversation: ConversationEntity): Long

    @Insert
    suspend fun insertMessage(message: MessageEntity)

    @Query("UPDATE conversations SET updatedAt = :updatedAt WHERE id = :conversationId")
    suspend fun updateConversationTimestamp(conversationId: Long, updatedAt: Long)

    @Query("SELECT * FROM conversations ORDER BY updatedAt DESC")
    fun observeConversations(): Flow<List<ConversationEntity>>

    @Query("SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY createdAt, id")
    fun observeMessages(conversationId: Long): Flow<List<MessageEntity>>

    @Query(
        "SELECT * FROM messages WHERE conversationId = :conversationId " +
            "ORDER BY createdAt DESC, id DESC LIMIT 1"
    )
    suspend fun getLatestMessage(conversationId: Long): MessageEntity?

    @Query("DELETE FROM conversations WHERE id = :conversationId")
    suspend fun deleteConversation(conversationId: Long)

    @Query("DELETE FROM conversations")
    suspend fun deleteAllConversations()
}
