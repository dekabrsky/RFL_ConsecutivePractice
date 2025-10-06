package ru.dekabrsky.rfl.news.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.dekabrsky.rfl.news.data.api.NewsApi
import ru.dekabrsky.rfl.news.data.mapper.NewsResponseToEntityMapper

class NewsRepository(
    private val api: NewsApi,
    private val mapper: NewsResponseToEntityMapper,
) {
    suspend fun getNews() = withContext(Dispatchers.IO) {
        val response = api.getNews()
        mapper.mapResponse(response)
    }
}