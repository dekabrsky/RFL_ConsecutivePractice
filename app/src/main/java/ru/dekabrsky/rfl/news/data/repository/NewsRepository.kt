package ru.dekabrsky.rfl.news.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.dekabrsky.rfl.core.orNow
import ru.dekabrsky.rfl.core.tryParseServerDate
import ru.dekabrsky.rfl.news.data.api.NewsApi
import ru.dekabrsky.rfl.news.data.db.NewsDatabase
import ru.dekabrsky.rfl.news.data.entity.NewsDbEntity
import ru.dekabrsky.rfl.news.data.mapper.NewsResponseToEntityMapper
import ru.dekabrsky.rfl.news.domain.model.NewsEntity
import java.time.LocalDateTime
import kotlin.text.insert

class NewsRepository(
    private val api: NewsApi,
    private val mapper: NewsResponseToEntityMapper,
    private val dataStore: DataStore<Preferences>,
    private val db: NewsDatabase,
) {
    private val newFirstKey = booleanPreferencesKey(NEW_FIRST_KEY)

    suspend fun getNews(newFirst: Boolean) = withContext(Dispatchers.IO) {
        val response =
            api.getNews(orderBy = "$CREATE_TIME_KEY ${if (newFirst) CREATE_TIME_DESC else CREATE_TIME_ASC}")
        mapper.mapResponse(response)
    }

    suspend fun setNewFirstSettings(newFirst: Boolean) = withContext(Dispatchers.IO) {
        dataStore.edit {
            it[newFirstKey] = newFirst
        }
    }

    fun observeNewFirstSettings(): Flow<Boolean> =
        dataStore.data.map { it[newFirstKey] ?: false }

    suspend fun getFavorites() =
        withContext(Dispatchers.IO) {
            db.newsDao().getAll().map {
                NewsEntity(
                    id = it.id.toString(),
                    title = it.title.orEmpty(),
                    text = it.text.orEmpty(),
                    time = LocalDateTime.now(),
                    imageUrl = null,
                )
            }
        }

    suspend fun saveFavorite(news: NewsEntity) =
        withContext(Dispatchers.IO) {
            db.newsDao().insert(
                NewsDbEntity(
                    title = news.title,
                    text = news.text,
                )
            )
        }

    companion object {
        private const val NEW_FIRST_KEY = "NEW_FIRST_KEY"

        private const val CREATE_TIME_KEY = "createTime"
        private const val CREATE_TIME_ASC = "asc"
        private const val CREATE_TIME_DESC = "desc"
    }
}