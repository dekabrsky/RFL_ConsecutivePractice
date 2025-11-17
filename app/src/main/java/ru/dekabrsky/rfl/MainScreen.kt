package ru.dekabrsky.rfl

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.ui.NavDisplay
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import ru.dekabrsky.core.navigation.EntryProviderInstaller
import ru.dekabrsky.core.navigation.Route
import ru.dekabrsky.core.navigation.TopLevelBackStack
import ru.dekabrsky.news.news.di.NEWS_QUALIFIER
import ru.dekabrsky.news.news.presentation.screen.NewsListScreen
import ru.dekabrsky.players.impl.presentation.PlayersScreen
import kotlin.getValue

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
    val topLevelBackStack by inject<TopLevelBackStack<Route>>(clazz = TopLevelBackStack::class.java)
    val dialogStrategy = remember { DialogSceneStrategy<Route>() }

    val newsEntryProvider by inject<EntryProviderInstaller>(
        clazz = EntryProviderInstaller::class.java,
        qualifier = named(NEWS_QUALIFIER)
    )

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
            sceneStrategy = dialogStrategy,
            entryDecorators = listOf(
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = entryProvider {
                entry<Players> {
                    PlayersScreen()
                }
                entry<News> {
                    NewsListScreen()
                }
                newsEntryProvider.let { builder -> this.builder() }
            }
        )
    }
}
