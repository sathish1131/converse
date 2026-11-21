package com.thinkdifferent.converse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.thinkdifferent.converse.ui.screens.HistoryScreen
import com.thinkdifferent.converse.ui.screens.HomeScreen
import com.thinkdifferent.converse.ui.screens.SettingScreen
import com.thinkdifferent.converse.ui.viewmodel.ConversationViewModel
import com.thinkdifferent.converse.ui.viewmodel.LanguageViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    languageViewModel: LanguageViewModel,
    conversationViewModel: ConversationViewModel,
    onLanguageSettingsSaved: () -> Unit,
    onConversationSelected: (Long) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(conversationViewModel = conversationViewModel)
        }
        composable(Screen.History.route) {
            HistoryScreen(
                conversationViewModel = conversationViewModel,
                onConversationClick = { conversation ->
                    onConversationSelected(conversation.id)
                }
            )
        }
        composable(Screen.Settings.route) {
            SettingScreen(
                languageViewModel = languageViewModel,
                onSave = onLanguageSettingsSaved
            )
        }
    }
}