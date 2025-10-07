package ru.dekabrsky.rfl.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.dekabrsky.rfl.news.data.mapper.NewsResponseToEntityMapper
import ru.dekabrsky.rfl.news.data.model.NewsApi
import ru.dekabrsky.rfl.news.data.repository.NewsRepository
import ru.dekabrsky.rfl.news.domain.interactor.NewsInteractor
import ru.dekabrsky.rfl.news.presentation.viewModel.NewsDetailsViewModel
import ru.dekabrsky.rfl.news.presentation.viewModel.NewsListViewModel

val newsFeatureModule = module {
    viewModel { NewsDetailsViewModel(get(), get()) }
    viewModel { NewsListViewModel(get(), get()) }

    single { get<Retrofit>().create(NewsApi::class.java) }

    factory { NewsResponseToEntityMapper() }
    single { NewsRepository(get(), get()) }

    single { NewsInteractor(get()) }
}