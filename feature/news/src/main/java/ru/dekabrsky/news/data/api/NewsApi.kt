package ru.dekabrsky.news.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.dekabrsky.news.data.model.NewsListResponse

interface NewsApi {
    @GET("documents/news")
    suspend fun getNews(
        @Query("orderBy") orderBy: String,
    ): NewsListResponse
}