package ru.dekabrsky.news.news.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import ru.dekabrsky.core.utils.formatDateTime
import ru.dekabrsky.core.utils.launchLoadingAndError
import ru.dekabrsky.core.navigation.Route
import ru.dekabrsky.core.navigation.TopLevelBackStack
import ru.dekabrsky.news.news.di.NewsDetails
import ru.dekabrsky.news.news.di.NewsSettings
import ru.dekabrsky.news.news.domain.interactor.NewsInteractor
import ru.dekabrsky.news.news.domain.model.NewsEntity
import ru.dekabrsky.news.news.presentation.model.NewsListFilter
import ru.dekabrsky.news.news.presentation.model.NewsListViewState
import ru.dekabrsky.news.news.presentation.model.NewsUiModel

class NewsListViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val interactor: NewsInteractor,
): ViewModel() {
    private val mutableState = MutableStateFlow(NewsListViewState())
    val viewState = mutableState.asStateFlow()

    init {
        loadNews()
    }

    fun onNewsClick(news: NewsUiModel) {
        topLevelBackStack.add(NewsDetails(news))
    }

    fun onRetryClick() = loadNews()

    fun onSettingsClick() {
        topLevelBackStack.add(NewsSettings)
    }

    fun onFilterChange(filter: NewsListFilter) {
        mutableState.update { it.copy(currentFilter = filter) }
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launchLoadingAndError(
            handleError = { e ->
                updateState(NewsListViewState.State.Error(e.localizedMessage.orEmpty()))
            }
        ) {
            updateState(NewsListViewState.State.Loading)

            interactor.observeNewFirstSettings()
                .onEach { updateState(NewsListViewState.State.Loading) }
                .map {
                    if (viewState.value.currentFilter == NewsListFilter.ALL) {
                        interactor.getNews(it)
                    } else {
                        interactor.getFavorites()
                    }
                }
                .collect { news ->
                    updateState(NewsListViewState.State.Success(mapToUi(news)))
                }
        }
    }

    private fun updateState(state: NewsListViewState.State) =
        mutableState.update { it.copy(state = state) }

    private fun mapToUi(news: List<NewsEntity>): List<NewsUiModel> = news.map { news ->
        NewsUiModel(
            id = news.id,
            title = news.title,
            text = news.text,
            imageUrl = news.imageUrl,
            time = formatDateTime(news.time),
        )
    }
}