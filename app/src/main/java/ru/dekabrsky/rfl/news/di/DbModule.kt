package ru.dekabrsky.rfl.news.di

import android.content.Context
import androidx.room.Room
import org.koin.dsl.module
import ru.dekabrsky.rfl.news.data.database.NewsDatabase

val dbModule = module {
    single { DatabaseBuilder.getInstance(get()) }
}

object DatabaseBuilder {
    private var INSTANCE: NewsDatabase? = null

    fun getInstance(context: Context): NewsDatabase {
        if (INSTANCE == null) {
            synchronized(NewsDatabase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            "news"
        ).build()
}