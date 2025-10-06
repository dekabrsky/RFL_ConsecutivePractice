package ru.dekabrsky.rfl.news.domain.interactor

import ru.dekabrsky.rfl.news.data.repository.NewsRepository

class NewsInteractor(
    private val repository: NewsRepository
) {
    suspend fun getNews() = repository.getNews()
}