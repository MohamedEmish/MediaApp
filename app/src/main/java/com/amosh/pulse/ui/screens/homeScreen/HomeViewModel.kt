package com.amosh.pulse.ui.screens.homeScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.lifecycle.viewModelScope
import com.amosh.pulse.R
import com.amosh.pulse.core.data.di.IoDispatcher
import com.amosh.pulse.core.domain.model.Section
import com.amosh.pulse.core.domain.model.UserData
import com.amosh.pulse.core.domain.useCases.GetHomeSectionsUseCase
import com.amosh.pulse.core.domain.useCases.GetUserDataUseCase
import com.amosh.pulse.core.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.delayEach
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeSectionsUseCase: GetHomeSectionsUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    private val _userDataState = MutableStateFlow(UserData())
    val userDataState = _userDataState.asStateFlow()

    private var currentPage = 1
    private val loadedSections: MutableList<Section> = mutableListOf()
    private var totalPages: Int? = null

    init {
        getStoredUserData()
        handleGetHomeSections(false)
    }

    override fun createInitialState(): HomeContract.State {
        return HomeContract.State(
            state = HomeContract.HomeState.Idle,
        )
    }

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnFetchHomeSections -> handleGetHomeSections(shouldRefresh = event.shouldRefresh)
        }
    }

    private fun handleGetHomeSections(shouldRefresh: Boolean) {
        if (shouldRefresh) {
            currentPage = 1
            loadedSections.clear()
        }
        if (currentPage == totalPages) {
            return
        }

        getHomeSectionsUseCase.getHomeSections(currentPage)
            .onStart { setState { copy(HomeContract.HomeState.Loading) } }
            .map { response ->
                totalPages = response.pagination?.totalPages
                currentPage += 1
                when {
                    response.sections.isNullOrEmpty() -> HomeContract.HomeState.Empty
                    else -> HomeContract.HomeState.Success(response.sections ?: listOf())
                }
            }
            .onEach { setState { copy(it) } }
            .catch {
                it.printStackTrace()
                setEffect { HomeContract.Effect.ShowError(messageRes = R.string.something_went_wrong) }
                setState { copy(HomeContract.HomeState.Empty) }
            }
            .flowOn(ioDispatcher)
            .launchIn(viewModelScope)
    }

    private fun getStoredUserData() = getUserDataUseCase.getUserData()
        .onEach { _userDataState.emit(it) }
        .catch {
            setEffect {
                HomeContract.Effect.ShowError(
                    messageRes = R.string.something_went_wrong
                )
            }
        }
        .flowOn(ioDispatcher)
        .launchIn(viewModelScope)

}