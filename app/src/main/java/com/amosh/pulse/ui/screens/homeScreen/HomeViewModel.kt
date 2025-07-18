package com.amosh.pulse.ui.screens.homeScreen

import androidx.lifecycle.viewModelScope
import com.amosh.pulse.R
import com.amosh.pulse.core.data.di.IoDispatcher
import com.amosh.pulse.core.domain.ConnectionChecker
import com.amosh.pulse.core.domain.model.UserData
import com.amosh.pulse.core.domain.useCases.GetHomeSectionsUseCase
import com.amosh.pulse.core.domain.useCases.GetUserDataUseCase
import com.amosh.pulse.core.ui.base.BaseViewModel
import com.amosh.pulse.model.SectionsUiItem
import com.amosh.pulse.ui.mapper.SectionItemUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeSectionsUseCase: GetHomeSectionsUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val mapper: SectionItemUiMapper,
    private val connectionChecker: ConnectionChecker,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    private val _userDataState = MutableStateFlow(UserData())
    val userDataState = _userDataState.asStateFlow()

    private var currentPage = 1
    private val loadedSections: MutableList<SectionsUiItem> = mutableListOf()
    private var totalPages: Int? = null

    private val fetchSectionsEvent = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)

    init {
        getStoredUserData()
        fetchSectionsEvent
            .debounce(400)
            .onEach { shouldRefresh ->
                if (connectionChecker.hasInternetConnection()) {
                    handleGetHomeSections(shouldRefresh)
                } else {
                    setState { copy(HomeContract.HomeState.NoInternet) }
                }
            }
            .launchIn(viewModelScope)
        checkConnectionBeforeCall(false)
    }

    override fun createInitialState(): HomeContract.State {
        return HomeContract.State(
            state = HomeContract.HomeState.Idle,
        )
    }

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnFetchHomeSections -> checkConnectionBeforeCall(shouldRefresh = event.shouldRefresh)
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
            .onStart {
                if (loadedSections.isEmpty()) {
                    setState { copy(HomeContract.HomeState.Loading) }
                }
            }
            .map { response ->
                totalPages = response.pagination?.totalPages
                currentPage += 1

                when {
                    response.sections.isNullOrEmpty() && loadedSections.isEmpty() -> {
                        HomeContract.HomeState.Empty
                    }
                    else -> {
                        val newSections = mapper.fromList(response.sections ?: listOf())

                        newSections.forEach { newSection ->
                            val existingSectionIndex = loadedSections.indexOfFirst { it.name == newSection.name }
                            if (existingSectionIndex != -1) {
                                val existingSection = loadedSections[existingSectionIndex]
                                val mergedContent = (existingSection.content?.toMutableList() ?: mutableListOf()).apply {
                                    addAll(newSection.content ?: listOf())
                                }
                                loadedSections[existingSectionIndex] = existingSection.copy(content = mergedContent)
                            } else {
                                loadedSections.add(newSection)
                            }
                        }

                        HomeContract.HomeState.Success(loadedSections.toList())
                    }
                }
            }
            .onEach { setState { copy(it) } }
            .catch {
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

    private fun checkConnectionBeforeCall(shouldRefresh: Boolean) = viewModelScope.launch {
        fetchSectionsEvent.emit(shouldRefresh)
    }
}