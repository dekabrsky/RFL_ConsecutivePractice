package ru.dekabrsky.rfl.news.domain.model

import java.time.LocalDateTime

class NewsEntity(
    val id: String,
    val title: String,
    val text: String?,
    val time: LocalDateTime,
    val imageUrl: String?,
)