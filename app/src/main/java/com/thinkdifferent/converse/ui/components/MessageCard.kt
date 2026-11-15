package com.thinkdifferent.converse.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thinkdifferent.converse.domain.model.Message

@Composable
fun MessageCard(
    message: Message,
    onAudioClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${message.source.displayName} -> ${message.target.displayName}",
                    style = MaterialTheme.typography.titleSmall
                )
                IconButton(
                    onClick = onAudioClick
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Play Audio"
                    )
                }
            }
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Input:",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = message.input,
                style = MaterialTheme.typography.bodyLarge
            )
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Translation:",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = message.translated,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Transliteration:",
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = message.transliterated,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}