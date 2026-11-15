package com.thinkdifferent.converse.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thinkdifferent.converse.ui.navigation.Screen

@Composable
fun NavDrawer(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        NavDrawerItem.Home,
        NavDrawerItem.Settings,
        NavDrawerItem.History
    )
    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Converse",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        HorizontalDivider()
        items.forEach {
            item ->
            NavigationDrawerItem(
                label = {
                    Text(text = item.title)
                },
                selected = currentRoute == item.route,
                onClick = {
                    onNavigate(item.route)
                }
            )
        }
    }
}

sealed class NavDrawerItem(
    val title: String,
    val route: String
) {
    data object Home : NavDrawerItem(
        title = "Home",
        route = Screen.Home.route
    )
    data object Settings : NavDrawerItem(
        title = "Settings",
        route = Screen.Settings.route
    )
    data object History : NavDrawerItem(
        title = "History",
        route = Screen.History.route
    )
}