package ru.dekabrsky.rfl.news.presentation.model

data class NewsDetailsViewState(
    val news: NewsUiModel,
    val rating: Float = 0f,
) {
    val userVoteVisible get() = rating != 0f
}
