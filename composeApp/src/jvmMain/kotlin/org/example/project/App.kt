package org.example.project

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import org.example.project.common.Screen
import org.example.project.screen.UserDetailsScreen
import org.example.project.screen.UserListScreen

@Composable
@Preview
fun App() {

    var currentScreen by remember { mutableStateOf<Screen>(Screen.UserLst) }

    when(val screen = currentScreen) {
        is Screen.UserLst -> {
            Navigator(UserListScreen(onUserClick = { userId -> currentScreen = Screen.UserDetails(userId)})) { navigator ->
                SlideTransition(navigator)
            }
        }

        is Screen.UserDetails -> {
            Navigator(UserDetailsScreen(screen.userId, onGoBackClicked = { currentScreen = Screen.UserLst })) { navigator ->
                SlideTransition(navigator)
            }
        }
    }


}
