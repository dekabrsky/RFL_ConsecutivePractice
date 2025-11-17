package ru.dekabrsky.news.news.domain.interactor

import ru.dekabrsky.news.news.data.repository.NewsRepository
import ru.dekabrsky.news.news.domain.model.NewsEntity

class NewsInteractor(
    private val repository: NewsRepository
) {
    suspend fun getNews(newFirst: Boolean) = repository.getNews(newFirst)

    fun observeNewFirstSettings() = repository.observeNewFirstSettings()

    suspend fun setNewFirstSetting(newFirst: Boolean) =
        repository.setNewFirstSettings(newFirst)

    suspend fun saveFavorite(news: NewsEntity) = repository.saveFavorite(news)

    suspend fun getFavorites() = repository.getFavorites()
}