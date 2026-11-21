package com.thinkdifferent.converse.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.thinkdifferent.converse.data.db.ConverseDatabase
import com.thinkdifferent.converse.data.repository.ConversationRepository
import com.thinkdifferent.converse.domain.enum.Language
import com.thinkdifferent.converse.domain.model.Conversation
import com.thinkdifferent.converse.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ConversationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ConversationRepository(
        ConverseDatabase.getInstance(application)
    )
    private val conversationMutex = Mutex()
    private val _activeConversationId = MutableStateFlow<Long?>(null)
    val activeConversationId = _activeConversationId.asStateFlow()
    private val _conversationVersion = MutableStateFlow(0)
    val conversationVersion = _conversationVersion.asStateFlow()

    fun conversations(): Flow<List<Conversation>> = repository.observeConversations()

    fun messages(conversationId: Long?): Flow<List<Message>> =
        conversationId?.let(repository::observeMessages) ?: emptyFlow()

    suspend fun send(input: String, source: Language, target: Language) {
        val message = input.trim()
        if (message.isEmpty()) return

        conversationMutex.withLock {
            val savedId = repository.saveMessage(
                existingConversationId = _activeConversationId.value,
                input = message,
                source = source,
                target = target,
                translated = message,
                transliterated = message
            )
            _activeConversationId.value = savedId
        }
    }

    suspend fun startNewConversation() {
        conversationMutex.withLock {
            _activeConversationId.value = null
            _conversationVersion.value += 1
        }
    }

    suspend fun openConversation(conversationId: Long): Message? =
        conversationMutex.withLock {
            val latestMessage = repository.getLatestMessage(conversationId)
            _activeConversationId.value = conversationId
            _conversationVersion.value += 1
            latestMessage
        }

    fun deleteConversation(conversationId: Long) {
        viewModelScope.launch {
            conversationMutex.withLock {
                repository.deleteConversation(conversationId)
                if (_activeConversationId.value == conversationId) {
                    _activeConversationId.value = null
                    _conversationVersion.value += 1
                }
            }
        }
    }

    fun deleteAllConversations() {
        viewModelScope.launch {
            conversationMutex.withLock {
                repository.deleteAllConversations()
                _activeConversationId.value = null
                _conversationVersion.value += 1
            }
        }
    }
}
