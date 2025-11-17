package ru.dekabrsky.news.news.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.dekabrsky.news.news.data.model.NewsDbEntity

@Dao
interface NewsDao {
    @Query("SELECT * FROM NewsDbEntity")
    suspend fun getAll(): List<NewsDbEntity>

    @Insert
    suspend fun insert(driverDbEntity: NewsDbEntity)
}