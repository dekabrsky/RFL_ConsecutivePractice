package ru.dekabrsky.rfl.news.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.dekabrsky.rfl.news.data.model.NewsListResponse

interface NewsApi {
    @GET("documents/news")
    suspend fun getNews(
        @Query("orderBy") orderBy: String = CREATE_TIME_KEY,
    ): NewsListResponse

    companion object {
        private const val CREATE_TIME_KEY = "createTime desc"
    }
}