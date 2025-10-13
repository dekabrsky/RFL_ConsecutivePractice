package ru.dekabrsky.rfl.news.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.dekabrsky.rfl.news.data.dao.NewsDao
import ru.dekabrsky.rfl.news.data.model.NewsDbEntity

@Database(entities = [NewsDbEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}