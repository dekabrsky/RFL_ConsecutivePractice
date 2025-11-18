package ru.dekabrsky.news.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.dekabrsky.news.data.dao.NewsDao
import ru.dekabrsky.news.data.model.NewsDbEntity

@Database(entities = [NewsDbEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}