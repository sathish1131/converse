package com.thinkdifferent.converse.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thinkdifferent.converse.ui.navigation.Screen
import com.thinkdifferent.converse.ui.viewmodel.LanguageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(
    languageViewModel: LanguageViewModel,
    currentRoute: String?,
    onMenuClick: () -> Unit = {},
    onNewConversationClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val languageState by languageViewModel.state.collectAsState()
    val title = when(currentRoute) {
        Screen.Home.route ->
            "${languageState.source.displayName} \u21C4 ${languageState.target.displayName}"
        Screen.Settings.route ->
            "Settings"
        Screen.History.route ->
            "History"
        else ->
            "Converse"
    }
    val home = currentRoute === Screen.Home.route
    Surface(
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().statusBarsPadding()
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        modifier = Modifier.clickable(
                            enabled = home,
                            onClick = {
                                languageViewModel.reverse()
                            }
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onMenuClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu"
                        )
                    }
                },
                actions = {
                    if (currentRoute === Screen.Home.route) {
                        IconButton(
                            onClick = onNewConversationClick
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddCircleOutline,
                                contentDescription = "New Conversation"
                            )
                        }
                    } else if (currentRoute === Screen.History.route) {
                        IconButton(
                            onClick = onDeleteClick
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete History"
                            )
                        }
                    }
                }
            )
        }
    }
}