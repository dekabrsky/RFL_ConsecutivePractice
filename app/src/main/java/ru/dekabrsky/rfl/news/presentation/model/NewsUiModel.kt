package ru.dekabrsky.rfl.news.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsUiModel(
    val id: String,
    val title: String,
    val text: String?,
    val time: String,
    val imageUrl: String?,
)
