package com.thinkdifferent.converse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.thinkdifferent.converse.ui.components.InputSection
import com.thinkdifferent.converse.ui.components.NavDrawer
import com.thinkdifferent.converse.ui.components.TopNavBar
import com.thinkdifferent.converse.ui.navigation.NavGraph
import com.thinkdifferent.converse.ui.navigation.Screen
import com.thinkdifferent.converse.ui.theme.ConverseTheme
import com.thinkdifferent.converse.ui.viewmodel.LanguageViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConverseTheme {
                ConverseApp()
            }
        }
    }
}

@Composable
fun ConverseApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()
    val languageViewModel: LanguageViewModel = viewModel()
    val home = currentRoute === Screen.Home.route
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NavDrawer(
                currentRoute = currentRoute,
                onNavigate = {
                    route ->
                    scope.launch {
                        drawerState.close()
                    }
                    navController.navigate(route) {
                        popUpTo(
                            navController.graph.startDestinationId
                        )
                        launchSingleTop = true
                    }
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopNavBar(
                    languageViewModel = languageViewModel,
                    currentRoute = currentRoute,
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onNewConversationClick = {

                    },
                    onDeleteClick = {

                    }
                )
            },
            bottomBar = {
                if (home) {
                    InputSection()
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                NavGraph(
                    navController = navController,
                    languageViewModel = languageViewModel
                )
            }
        }
    }
}