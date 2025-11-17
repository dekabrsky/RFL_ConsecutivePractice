package ru.dekabrsky.rfl.news.data.mapper

import ru.dekabrsky.core.utils.orNow
import ru.dekabrsky.core.utils.tryParseServerDate
import ru.dekabrsky.rfl.news.data.model.NewsListResponse
import ru.dekabrsky.rfl.news.domain.model.NewsEntity

class NewsResponseToEntityMapper {
    fun mapResponse(response: NewsListResponse): List<NewsEntity> {
        return response.documents?.map { doc ->
            NewsEntity(
                id = doc.name.orEmpty(),
                title = doc.fields?.title?.stringValue.orEmpty(),
                text = doc.fields?.text?.stringValue,
                imageUrl = doc.fields?.imageUrl?.stringValue,
                time = tryParseServerDate(doc.fields?.createTime?.timestampValue).orNow()
            )
        }.orEmpty()
    }
}
