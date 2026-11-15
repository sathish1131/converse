package com.thinkdifferent.converse.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thinkdifferent.converse.domain.enum.Language
import com.thinkdifferent.converse.domain.model.Message
import com.thinkdifferent.converse.ui.components.MessageCard

@Composable
fun HomeScreen() {
    val messages = listOf(
        Message(
            id = 1,
            source = Language.HINDI,
            target = Language.ENGLISH,
            input = "Hello",
            translated = "How",
            transliterated = "are you"
        ),
        Message(
            id = 1,
            source = Language.ENGLISH,
            target = Language.HINDI,
            input = "who",
            translated = "are",
            transliterated = "you"
        ),
        Message(
            id = 1,
            source = Language.HINDI,
            target = Language.ENGLISH,
            input = "Hello",
            translated = "How",
            transliterated = "are you"
        ),
        Message(
            id = 1,
            source = Language.ENGLISH,
            target = Language.HINDI,
            input = "who",
            translated = "are",
            transliterated = "you"
        ),
        Message(
            id = 1,
            source = Language.HINDI,
            target = Language.ENGLISH,
            input = "Hello",
            translated = "How",
            transliterated = "are you"
        )
    )
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = messages.size
        ) {
            index ->
            MessageCard(
                message = messages[index],
                onAudioClick = {}
            )
        }
    }
}