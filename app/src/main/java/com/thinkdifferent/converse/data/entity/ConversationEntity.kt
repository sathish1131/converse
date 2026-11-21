package com.thinkdifferent.converse.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val source: String,
    val target: String,
    val title: String,
    val createdAt: Long,
    val updatedAt: Long
)
