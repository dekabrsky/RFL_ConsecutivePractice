package ru.dekabrsky.rfl.news.domain.interactor

import ru.dekabrsky.rfl.news.data.repository.NewsRepository

class NewsInteractor(
    private val repository: NewsRepository
) {
    suspend fun getNews(newFirst: Boolean) = repository.getNews(newFirst)

    fun observeNewFirstSettings() = repository.observeNewFirstSettings()

    suspend fun setNewFirstSetting(newFirst: Boolean) =
        repository.setNewFirstSettings(newFirst)
}