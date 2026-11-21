package com.thinkdifferent.converse.data.repository

import androidx.room.withTransaction
import com.thinkdifferent.converse.data.dao.ConversationDao
import com.thinkdifferent.converse.data.db.ConverseDatabase
import com.thinkdifferent.converse.data.entity.ConversationEntity
import com.thinkdifferent.converse.data.entity.MessageEntity
import com.thinkdifferent.converse.domain.enum.Language
import com.thinkdifferent.converse.domain.model.Conversation
import com.thinkdifferent.converse.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConversationRepository(
    private val database: ConverseDatabase
) {
    private val dao: ConversationDao = database.conversationDao()

    fun observeConversations(): Flow<List<Conversation>> =
        dao.observeConversations().map { rows ->
            rows.map { row ->
                Conversation(
                    id = row.id,
                    source = Language.valueOf(row.source),
                    target = Language.valueOf(row.target),
                    title = row.title,
                    createdAt = row.createdAt,
                    updatedAt = row.updatedAt
                )
            }
        }

    fun observeMessages(conversationId: Long): Flow<List<Message>> =
        dao.observeMessages(conversationId).map { rows ->
            rows.map { row ->
                Message(
                    id = row.id,
                    source = Language.valueOf(row.source),
                    target = Language.valueOf(row.target),
                    input = row.input,
                    translated = row.translated,
                    transliterated = row.transliterated
                )
            }
        }

    suspend fun getLatestMessage(conversationId: Long): Message? =
        dao.getLatestMessage(conversationId)?.let { row ->
            Message(
                id = row.id,
                source = Language.valueOf(row.source),
                target = Language.valueOf(row.target),
                input = row.input,
                translated = row.translated,
                transliterated = row.transliterated
            )
        }

    suspend fun saveMessage(
        existingConversationId: Long?,
        input: String,
        source: Language,
        target: Language,
        translated: String,
        transliterated: String
    ): Long = database.withTransaction {
        val now = System.currentTimeMillis()
        val conversationId = existingConversationId ?: dao.insertConversation(
            ConversationEntity(
                source = source.name,
                target = target.name,
                title = input.take(60),
                createdAt = now,
                updatedAt = now
            )
        )
        dao.insertMessage(
            MessageEntity(
                conversationId = conversationId,
                source = source.name,
                target = target.name,
                input = input,
                translated = translated,
                transliterated = transliterated,
                createdAt = now
            )
        )
        dao.updateConversationTimestamp(conversationId, now)
        conversationId
    }

    suspend fun deleteConversation(conversationId: Long) =
        dao.deleteConversation(conversationId)

    suspend fun deleteAllConversations() =
        dao.deleteAllConversations()
}
