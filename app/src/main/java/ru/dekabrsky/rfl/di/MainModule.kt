package ru.dekabrsky.rfl.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.dekabrsky.rfl.News
import ru.dekabrsky.rfl.navigation.Route
import ru.dekabrsky.rfl.navigation.TopLevelBackStack
import ru.dekabrsky.rfl.news.presentation.viewModel.NewsDetailsViewModel

val mainModule = module {
    single { TopLevelBackStack<Route>(News) }

    viewModel { NewsDetailsViewModel(get(), get()) }
}