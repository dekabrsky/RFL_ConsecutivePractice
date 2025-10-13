package ru.dekabrsky.rfl.news.data.repository

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.dekabrsky.rfl.news.data.api.NewsApi
import ru.dekabrsky.rfl.news.data.mapper.NewsResponseToEntityMapper

class NewsRepository(
    private val api: NewsApi,
    private val mapper: NewsResponseToEntityMapper,
    private val dataStore: DataStore<Preferences>,
    private val preferencesEditor: SharedPreferences.Editor,
    private val preferences: SharedPreferences,
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

    companion object {
        private const val NEW_FIRST_KEY = "NEW_FIRST_KEY"

        private const val CREATE_TIME_KEY = "createTime"
        private const val CREATE_TIME_ASC = "asc"
        private const val CREATE_TIME_DESC = "desc"
    }
}