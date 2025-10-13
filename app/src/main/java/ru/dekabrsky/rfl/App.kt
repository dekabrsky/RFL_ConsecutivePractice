package ru.dekabrsky.rfl

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.dekabrsky.rfl.di.mainModule
import ru.dekabrsky.rfl.di.networkModule
import ru.dekabrsky.rfl.news.di.dbModule
import ru.dekabrsky.rfl.news.di.newsFeatureModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(mainModule, networkModule, newsFeatureModule, dbModule)
        }
    }
}