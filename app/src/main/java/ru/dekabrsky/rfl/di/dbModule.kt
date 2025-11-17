package ru.dekabrsky.rfl.di

import android.content.Context
import androidx.room.Room
import org.koin.dsl.module
import ru.dekabrsky.rfl.news.data.db.NewsDatabase

val dbModule = module {
    single { DatabaseBuilder.getInstance(get()) }
}

object DatabaseBuilder {

    fun getInstance(context: Context) = buildRoomDB(context)

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            NewsDatabase::class.java,
            "news"
        ).build()
}