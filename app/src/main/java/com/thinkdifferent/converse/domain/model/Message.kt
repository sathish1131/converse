package com.thinkdifferent.converse.domain.model

import com.thinkdifferent.converse.domain.enum.Language

data class Message(
    val id: Long,
    val source: Language,
    val target: Language,
    val input: String,
    val translated: String,
    val transliterated: String
)
