package ru.dekabrsky.news.news.data.repository

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.dekabrsky.core.utils.orNow
import ru.dekabrsky.core.utils.tryParseServerDate
import ru.dekabrsky.news.news.data.api.NewsApi
import ru.dekabrsky.news.news.data.database.NewsDatabase
import ru.dekabrsky.news.news.data.mapper.NewsResponseToEntityMapper
import ru.dekabrsky.news.news.data.model.NewsDbEntity
import ru.dekabrsky.news.news.domain.model.NewsEntity

class NewsRepository(
    private val api: NewsApi,
    private val mapper: NewsResponseToEntityMapper,
    private val dataStore: DataStore<Preferences>,
    private val preferencesEditor: SharedPreferences.Editor,
    private val preferences: SharedPreferences,
    private val db: NewsDatabase,
) {
    private val newFirstKey = booleanPreferencesKey(NEW_FIRST_KEY)

    suspend fun getNews(newFirst: Boolean) = withContext(Dispatchers.IO) {
        val response =
            api.getNews(orderBy = "$CREATE_TIME_KEY ${if (newFirst) CREATE_TIME_DESC else CREATE_TIME_ASC}")
        mapper.mapResponse(response)
    }

    fun observeNewFirstSettings(): Flow<Boolean> =
        dataStore.data.map { it[newFirstKey] ?: false }
        //flow { preferences.getBoolean(NEW_FIRST_KEY, false) }

    suspend fun setNewFirstSettings(newFirst: Boolean) = withContext(Dispatchers.IO) {
        dataStore.edit {
            it[newFirstKey] = newFirst
        }
        //preferencesEditor.putBoolean(NEW_FIRST_KEY, newFirst)
    }

    suspend fun saveFavorite(news: NewsEntity) =
        withContext(Dispatchers.IO) {
            db.newsDao().insert(
                NewsDbEntity(
                    title = news.title,
                    text = news.text,
                    time = news.time.toString(),
                )
            )
        }

    suspend fun getFavorites() =
        withContext(Dispatchers.IO) {
            db.newsDao().getAll().map {
                NewsEntity(
                    id = it.id.toString(),
                    title = it.title.orEmpty(),
                    text = it.text.orEmpty(),
                    time = tryParseServerDate(it.time).orNow(),
                    imageUrl = null,
                )
            }
        }

    companion object {
        private const val NEW_FIRST_KEY = "NEW_FIRST_KEY"

        private const val CREATE_TIME_KEY = "createTime"
        private const val CREATE_TIME_ASC = "asc"
        private const val CREATE_TIME_DESC = "desc"
    }
}