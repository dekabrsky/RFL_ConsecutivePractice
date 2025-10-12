package ru.dekabrsky.rfl.news.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.dekabrsky.rfl.News
import ru.dekabrsky.rfl.NewsDetails
import ru.dekabrsky.rfl.navigation.Route
import ru.dekabrsky.rfl.navigation.TopLevelBackStack
import ru.dekabrsky.rfl.news.presentation.MockData
import ru.dekabrsky.rfl.news.presentation.model.NewsListViewState
import ru.dekabrsky.rfl.news.presentation.model.NewsUiModel
import ru.dekabrsky.rfl.news.presentation.viewModel.NewsListViewModel
import ru.dekabrsky.rfl.uikit.FullscreenError
import ru.dekabrsky.rfl.uikit.FullscreenLoading

@Composable
fun NewsListScreen() {
    val viewModel = koinViewModel<NewsListViewModel>()
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    NewsListScreenContent(
        state.state,
        viewModel::onNewsClick,
        viewModel::onRetryClick,
    )
}

@Composable
private fun NewsListScreenContent(
    state: NewsListViewState.State,
    onNewsClick: (NewsUiModel) -> Unit = {},
    onRetryClick: () -> Unit = {},
) {
    when (state) {
        NewsListViewState.State.Loading -> {
            FullscreenLoading()
        }

        is NewsListViewState.State.Error -> {
            FullscreenError(
                retry = { onRetryClick() },
                text = state.error
            )
        }

        is NewsListViewState.State.Success -> {
            LazyColumn {
                state.data.forEach { news ->
                    item {
                        NewsListItem(news) { onNewsClick(it) }
                    }
                }
            }
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

@Preview(showBackground = true)
@Composable
fun NewsListPreview() {
    NewsListScreenContent(
        NewsListViewState.State.Success(MockData.getNews())
    )
}