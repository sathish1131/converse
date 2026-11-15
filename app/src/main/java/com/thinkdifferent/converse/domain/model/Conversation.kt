package com.thinkdifferent.converse.domain.model

import com.thinkdifferent.converse.domain.enum.Language

data class Conversation(
    val id: Long,
    val source: Language,
    val target: Language,
    val title: String,
    val createdAt: Long,
    val updatedAt: Long
)
