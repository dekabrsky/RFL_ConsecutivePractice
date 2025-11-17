package ru.dekabrsky.news.news.presentation.model

import ru.dekabrsky.players.api.domain.Player

data class NewsDetailsViewState(
    val news: NewsUiModel,
    val rating: Float = 0f,
    val player: Player? = null,
) {
    val userVoteVisible get() = rating != 0f
}
