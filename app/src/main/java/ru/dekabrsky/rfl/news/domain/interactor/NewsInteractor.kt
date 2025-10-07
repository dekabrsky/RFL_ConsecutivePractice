package ru.dekabrsky.rfl.news.domain.interactor

import ru.dekabrsky.rfl.news.data.repository.NewsRepository

class NewsInteractor(
    private val newsRepository: NewsRepository,
) {
    suspend fun getNews() = newsRepository.getNews()
}