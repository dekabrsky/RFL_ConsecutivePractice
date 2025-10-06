package ru.dekabrsky.rfl.news.presentation.model

data class NewsListViewState(
    val state: State = State.Loading,
) {
    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State
        data class Success(val data: List<NewsUiModel>) : State
    }
}