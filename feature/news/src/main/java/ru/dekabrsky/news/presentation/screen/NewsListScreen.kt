package ru.dekabrsky.news.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.dekabrsky.news.presentation.model.NewsListFilter
import ru.dekabrsky.news.presentation.model.NewsListViewState
import ru.dekabrsky.news.presentation.model.NewsUiModel
import ru.dekabrsky.news.presentation.viewModel.NewsListViewModel
import ru.dekabrsky.uikit.uikit.FullscreenError
import ru.dekabrsky.uikit.uikit.FullscreenLoading
import ru.dekabrsky.uikit.uikit.Spacing

@Composable
fun NewsListScreen() {
    val viewModel = koinViewModel<NewsListViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    NewsListScreenContent(
        state,
        viewModel::onNewsClick,
        viewModel::onRetryClick,
        viewModel::onSettingsClick,
        viewModel::onFilterChange,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsListScreenContent(
    state: NewsListViewState,
    onNewsClick: (NewsUiModel) -> Unit = {},
    onRetryClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onFilterChange: (NewsListFilter) -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onSettingsClick() }) {
                Icon(Icons.Default.Settings, "Settings")
            }
        },
        topBar = {
            TopAppBar(
                { NewsListFilters(state, onFilterChange) },
                scrollBehavior = scrollBehavior
            )
        },
        contentWindowInsets = WindowInsets(),
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
    ) {
        Box(Modifier.padding(it)) {
            when (state.state) {
                NewsListViewState.State.Loading -> {
                    FullscreenLoading()
                }

                is NewsListViewState.State.Error -> {
                    FullscreenError(
                        retry = { onRetryClick() },
                        text = state.state.error
                    )
                }

                is NewsListViewState.State.Success -> {
                    LazyColumn {
                        state.state.data.forEach { news ->
                            item {
                                NewsListItem(news) { onNewsClick(it) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NewsListFilters(
    state: NewsListViewState,
    onFilterChange: (NewsListFilter) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(Spacing.small)
    ) {
        state.filters.forEach { filter ->
            FilterChip(
                selected = filter == state.currentFilter,
                label = { Text(filter.text) },
                onClick = { onFilterChange(filter) },
            )
        }
    }
}

@Composable
fun NewsListItem(news: NewsUiModel, onNewsClick: (NewsUiModel) -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onNewsClick(news) }
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = news.time,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            text = news.title,
            style = MaterialTheme.typography.titleMedium,
        )

        if (!news.text.isNullOrBlank()) {
            Text(
                text = news.text,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }

        HorizontalDivider()
    }
}
