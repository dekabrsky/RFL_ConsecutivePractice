package ru.dekabrsky.rfl

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import ru.dekabrsky.rfl.navigation.Route
import ru.dekabrsky.rfl.navigation.TopLevelBackStack

interface TopLevelRoute: Route {
    val icon: ImageVector
}
data object Players: TopLevelRoute {
    override val icon = Icons.Default.Face
}

data object News: TopLevelRoute {
    override val icon = Icons.AutoMirrored.Filled.List
}

@Composable
fun MainScreen() {
    val topLevelBackStack = remember { TopLevelBackStack<Route>(News) }

    Scaffold(bottomBar = {
        NavigationBar {
            listOf(Players, News).forEach { route ->
                NavigationBarItem(
                    icon = { Icon(route.icon, null) },
                    selected = topLevelBackStack.topLevelKey == route,
                    onClick = {
                        topLevelBackStack.addTopLevel(route)
                    }
                )
            }
        }
    }) { padding ->
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            modifier = Modifier.padding(padding),
            entryProvider = entryProvider {
                entry<Players> {
                    ContentBlue("Players")
                }
                entry<News> {
                    ContentGreen("News") { }
                }
            }
        )
    }
}

@Composable
fun ContentBlue(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
    )
}

@Composable
fun ContentGreen(text: String, content: @Composable () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Green)
    ) {
        Text(text)
        content()
    }
}