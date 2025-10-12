package ru.dekabrsky.rfl.news.presentation.viewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.dekabrsky.rfl.R
import ru.dekabrsky.rfl.navigation.Route
import ru.dekabrsky.rfl.navigation.TopLevelBackStack
import ru.dekabrsky.rfl.news.presentation.model.NewsDetailsViewState
import ru.dekabrsky.rfl.news.presentation.model.NewsUiModel

class NewsDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val news: NewsUiModel,
): ViewModel() {
    private val mutableState = MutableStateFlow(NewsDetailsViewState(news))
    val state = mutableState.asStateFlow()

    fun onRatingChanged(rating: Float) {
        mutableState.update { it.copy(rating = rating) }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}