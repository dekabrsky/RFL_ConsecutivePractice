package ru.dekabrsky.news.news.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.dekabrsky.core.navigation.Route
import ru.dekabrsky.core.navigation.TopLevelBackStack
import ru.dekabrsky.news.news.domain.interactor.NewsInteractor
import ru.dekabrsky.news.news.domain.model.NewsEntity
import ru.dekabrsky.news.news.presentation.model.NewsDetailsViewState
import ru.dekabrsky.news.news.presentation.model.NewsUiModel
import java.time.LocalDateTime

class NewsDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val news: NewsUiModel,
    private val interactor: NewsInteractor,
): ViewModel() {
    private val mutableState = MutableStateFlow(NewsDetailsViewState(news))
    val state = mutableState.asStateFlow()

    fun onRatingChanged(rating: Float) {
        mutableState.update { it.copy(rating = rating) }

        if (rating > 4f) {
            viewModelScope.launch {
                interactor.saveFavorite(
                    NewsEntity(
                        news.id,
                        news.title,
                        news.text,
                        LocalDateTime.now(),
                        news.imageUrl
                    )
                )
            }
        }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}