package com.amosh.pulse.ui.screens.searchScreen

import com.amosh.pulse.core.ui.base.UiEffect
import com.amosh.pulse.core.ui.base.UiEvent
import com.amosh.pulse.core.ui.base.UiState
import com.amosh.pulse.model.ContentUiItem

class SearchContract {
    sealed class Event : UiEvent {
        data class OnSearch(val query: String) : Event()
    }

    data class State(val state: SearchState) : UiState

    sealed class SearchState {
        data object Idle : SearchState()
        data object NoInternet : SearchState()
        data object Loading : SearchState()
        data object Empty : SearchState()
        data class Success(val content: List<ContentUiItem?>) : SearchState()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val messageRes: Int? = null) : Effect()
    }
}