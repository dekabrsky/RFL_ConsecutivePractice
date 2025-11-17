package ru.dekabrsky.news.news.di

import androidx.compose.ui.window.DialogProperties
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.scene.DialogSceneStrategy
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import ru.dekabrsky.core.navigation.EntryProviderInstaller
import ru.dekabrsky.core.navigation.Route
import ru.dekabrsky.news.news.data.api.NewsApi
import ru.dekabrsky.news.news.data.mapper.NewsResponseToEntityMapper
import ru.dekabrsky.news.news.data.repository.NewsRepository
import ru.dekabrsky.news.news.domain.interactor.NewsInteractor
import ru.dekabrsky.news.news.presentation.model.NewsUiModel
import ru.dekabrsky.news.news.presentation.screen.NewsDetailsDialog
import ru.dekabrsky.news.news.presentation.screen.NewsListScreen
import ru.dekabrsky.news.news.presentation.screen.NewsSettingsDialog
import ru.dekabrsky.news.news.presentation.viewModel.NewsDetailsViewModel
import ru.dekabrsky.news.news.presentation.viewModel.NewsListViewModel
import ru.dekabrsky.news.news.presentation.viewModel.NewsSettingsViewModel

const val NEWS_QUALIFIER = "newsQualifier"

data class NewsDetails(val news: NewsUiModel) : Route

data object NewsSettings : Route

val newsFeatureModule = module {
    single { get<Retrofit>().create(NewsApi::class.java) }

    factory { NewsResponseToEntityMapper() }
    single { NewsRepository(get(), get(), get(), get(), get(), get()) }

    single { NewsInteractor(get()) }

    viewModel { NewsListViewModel(get(), get()) }
    viewModel { NewsDetailsViewModel(get(), get(), get()) }
    viewModel { NewsSettingsViewModel(get(), get()) }

    factory<EntryProviderInstaller>(qualifier = named(NEWS_QUALIFIER)) {
        {
            entry<NewsDetails>(
                metadata = DialogSceneStrategy.dialog(DialogProperties())
            ) {
                NewsDetailsDialog(it.news)
            }
            entry<NewsSettings>(
                metadata = DialogSceneStrategy.dialog(DialogProperties())
            ) {
                NewsSettingsDialog()
            }
        }
    }
}