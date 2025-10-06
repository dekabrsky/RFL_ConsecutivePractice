package ru.dekabrsky.rfl.news.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.dekabrsky.rfl.news.data.api.NewsApi
import ru.dekabrsky.rfl.news.data.mapper.NewsResponseToEntityMapper
import ru.dekabrsky.rfl.news.data.repository.NewsRepository
import ru.dekabrsky.rfl.news.domain.interactor.NewsInteractor
import ru.dekabrsky.rfl.news.presentation.viewModel.NewsDetailsViewModel
import ru.dekabrsky.rfl.news.presentation.viewModel.NewsListViewModel

val newsFeatureModule = module {
    single { get<Retrofit>().create(NewsApi::class.java) }

    factory { NewsResponseToEntityMapper() }
    single { NewsRepository(get(), get()) }

    single { NewsInteractor(get()) }

    viewModel { NewsListViewModel(get(), get()) }
    viewModel { NewsDetailsViewModel(get(), get()) }
}