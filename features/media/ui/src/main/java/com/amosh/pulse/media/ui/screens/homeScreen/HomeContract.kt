package com.amosh.pulse.media.ui.screens.homeScreen

import com.amosh.pulse.core.ui.base.UiEffect
import com.amosh.pulse.core.ui.base.UiEvent
import com.amosh.pulse.core.ui.base.UiState
import com.amosh.pulse.media.ui.model.SectionsUiItem

class HomeContract {

    sealed class Event : UiEvent {
        data class OnFetchHomeSections(
            val shouldRefresh: Boolean = true
        ) : Event()
    }

    data class State(
        val state: HomeState,
    ) : UiState

    sealed class HomeState {
        data object Idle : HomeState()
        data object NoInternet : HomeState()
        data object Loading : HomeState()
        data object Empty : HomeState()
        data class Success(val sections: List<SectionsUiItem>) : HomeState()
    }

    sealed class Effect : UiEffect {
        data class ShowError(val messageRes: Int? = null) : Effect()
    }
}