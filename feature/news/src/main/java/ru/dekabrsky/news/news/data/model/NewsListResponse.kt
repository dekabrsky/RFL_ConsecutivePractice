package ru.dekabrsky.news.news.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
class NewsListResponse(
    val documents: List<NewsListDocument>?,
)

@Keep
@Serializable
class NewsListDocument(
    val name: String? = null,
    val fields: NewsFirestoreModel?,
)

@Keep
@Serializable
class NewsFirestoreModel(
    val id: StringFirestoreModel?,
    val text: StringFirestoreModel?,
    val title: StringFirestoreModel?,
    val imageUrl: StringFirestoreModel?,
    val createTime: TimestampFirestoreModel?,
)
