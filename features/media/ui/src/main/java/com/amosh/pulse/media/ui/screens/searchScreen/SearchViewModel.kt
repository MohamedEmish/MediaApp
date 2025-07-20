package com.amosh.pulse.media.ui.screens.searchScreen

import androidx.lifecycle.viewModelScope
import com.amosh.pulse.core.data.di.IoDispatcher
import com.amosh.pulse.core.domain.ConnectionChecker
import com.amosh.pulse.media.domain.useCase.GetHomeSectionsUseCase
import com.amosh.pulse.core.ui.R
import com.amosh.pulse.core.ui.base.BaseViewModel
import com.amosh.pulse.media.ui.mapper.SearchItemUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getHomeSectionsUseCase: GetHomeSectionsUseCase,
    private val mapper: SearchItemUiMapper,
    private val connectionChecker: ConnectionChecker,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BaseViewModel<SearchContract.Event, SearchContract.State, SearchContract.Effect>() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        observeQueryChange()
    }

    override fun createInitialState(): SearchContract.State {
        return SearchContract.State(
            state = SearchContract.SearchState.Idle,
        )
    }

    override fun handleEvent(event: SearchContract.Event) {
        when (event) {
            is SearchContract.Event.OnSearch -> _searchQuery.value = event.query
        }
    }

    private fun checkConnectionBeforeCall(query: String) = viewModelScope.launch {
        if (connectionChecker.hasInternetConnection()) {
            searchForResults(query)
        } else setState { copy(SearchContract.SearchState.NoInternet) }

    }

    private fun searchForResults(query: String) {
        getHomeSectionsUseCase.getHomeSearchSections(query)
            .onStart {
                setState { copy(SearchContract.SearchState.Loading) }
            }
            .map { response ->
                val allContent = response.sections.orEmpty()
                    .flatMap { it.content.orEmpty() }

                if (allContent.isEmpty()) {
                    SearchContract.SearchState.Empty
                } else {
                    SearchContract.SearchState.Success(mapper.fromList(allContent))
                }
            }
            .onEach { newState ->
                setState { copy(newState) }
            }
            .catch { exception ->
                setEffect {
                    SearchContract.Effect.ShowError(messageRes = R.string.something_went_wrong)
                }
                setState { copy(SearchContract.SearchState.Empty) }
            }
            .flowOn(ioDispatcher)
            .launchIn(viewModelScope)
    }

    private fun observeQueryChange() = viewModelScope.launch {
        _searchQuery
            .debounce(200)
            .filter { it.isNotBlank() }
            .distinctUntilChanged()
            .collectLatest { query ->
                checkConnectionBeforeCall(query)
            }
    }
}