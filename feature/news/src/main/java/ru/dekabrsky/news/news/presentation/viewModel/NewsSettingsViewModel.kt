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
import ru.dekabrsky.news.news.presentation.model.NewsSettingsState

class NewsSettingsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val interactor: NewsInteractor,
): ViewModel() {
    private val mutableState = MutableStateFlow(NewsSettingsState())
    val viewState = mutableState.asStateFlow()

    init {
        viewModelScope.launch {
            interactor.observeNewFirstSettings().collect { newFirst ->
                mutableState.update { it.copy(newFirst = newFirst) }
            }
        }
    }

    fun onNewFirstCheckedChange(isChecked: Boolean) {
        mutableState.update { it.copy(newFirst = isChecked) }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }

    fun onSaveClicked() {
        viewModelScope.launch {
            interactor.setNewFirstSetting(viewState.value.newFirst)
            onBack()
        }
    }
}