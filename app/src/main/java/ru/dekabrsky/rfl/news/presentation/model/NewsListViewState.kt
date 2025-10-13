package ru.dekabrsky.rfl.news.presentation.model

data class NewsListViewState(
    val state: State = State.Loading,
    val filters: List<NewsListFilter> = NewsListFilter.entries,
    val currentFilter: NewsListFilter = NewsListFilter.ALL,
) {
    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State
        data class Success(val data: List<NewsUiModel>) : State
    }
}

enum class NewsListFilter(val text: String) {
    ALL("Все"),
    FAVORITES("Избранные"),
}