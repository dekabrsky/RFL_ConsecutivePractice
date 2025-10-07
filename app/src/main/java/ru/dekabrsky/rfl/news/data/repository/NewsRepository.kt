package ru.dekabrsky.rfl.news.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dekabrsky.rfl.news.data.mapper.NewsResponseToEntityMapper
import ru.dekabrsky.rfl.news.data.model.NewsApi
import ru.dekabrsky.rfl.news.domain.model.NewsEntity

class NewsRepository(
    private val api: NewsApi,
    private val mapper: NewsResponseToEntityMapper,
) {
    suspend fun getNews(): List<NewsEntity> = withContext(Dispatchers.IO) {
        val response = api.getNews()
        mapper.mapResponse(response)
    }
}