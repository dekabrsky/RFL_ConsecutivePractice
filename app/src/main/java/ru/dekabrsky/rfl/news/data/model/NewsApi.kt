package ru.dekabrsky.rfl.news.data.model

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("documents/news")
    suspend fun getNews(
        @Query("orderBy") orderBy: String = CREATE_TIME_KEY,
    ): NewsListResponse

    companion object {
        private const val CREATE_TIME_KEY = "createTime desc"
    }
}