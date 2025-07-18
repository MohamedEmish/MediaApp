package com.amosh.pulse.viewModels

import app.cash.turbine.test
import com.amosh.pulse.BaseTest
import com.amosh.pulse.core.domain.ConnectionChecker
import com.amosh.pulse.core.domain.model.ContentItem
import com.amosh.pulse.core.domain.model.HomeSectionsResponse
import com.amosh.pulse.core.domain.model.Section
import com.amosh.pulse.core.domain.useCases.GetHomeSectionsUseCase
import com.amosh.pulse.model.ContentUiItem
import com.amosh.pulse.ui.mapper.SearchItemUiMapper
import com.amosh.pulse.ui.screens.searchScreen.SearchContract
import com.amosh.pulse.ui.screens.searchScreen.SearchViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalTime
class SearchViewModelTest : BaseTest() {
    private lateinit var viewModel: SearchViewModel
    private val getHomeSectionsUseCase = mockk<GetHomeSectionsUseCase>()
    private val mapper = mockk<SearchItemUiMapper>()
    private val connectionChecker = mockk<ConnectionChecker>()

    @Before
    fun setUp() {
        viewModel = SearchViewModel(
            getHomeSectionsUseCase,
            mapper,
            connectionChecker,
            Dispatchers.Main
        )
    }

    @Test
    fun `search should emit Loading then Success when results found`() = runTest {
        // Given
        val query = "test"
        val testContent = listOf(ContentItem(name = "Test Podcast"))
        val testContentUi = listOf(ContentUiItem.PodcastContentUi(name = "Test Podcast"))
        val response = HomeSectionsResponse(sections = listOf(Section(content = testContent)))

        coEvery { connectionChecker.hasInternetConnection() } returns true
        every { getHomeSectionsUseCase.getHomeSearchSections(query) } returns flowOf(response)
        every { mapper.fromList(any()) } returns testContentUi

        // When
        viewModel.handleEvent(SearchContract.Event.OnSearch(query))

        // Then
        viewModel.uiState.test {
            assertEquals(SearchContract.SearchState.Idle, awaitItem().state)
            assertEquals(SearchContract.SearchState.Loading, awaitItem().state)
            assertEquals(SearchContract.SearchState.Success(testContentUi), awaitItem().state)
        }
    }

    @Test
    fun `search should emit Loading then Empty when no results`() = runTest {
        // Given
        val query = "test"
        val response = HomeSectionsResponse(sections = emptyList())

        coEvery { connectionChecker.hasInternetConnection() } returns true
        every { getHomeSectionsUseCase.getHomeSearchSections(query) } returns flowOf(response)

        // When
        viewModel.handleEvent(SearchContract.Event.OnSearch(query))

        // Then
        viewModel.uiState.test {
            assertEquals(SearchContract.SearchState.Idle, awaitItem().state)
            assertEquals(SearchContract.SearchState.Loading, awaitItem().state)
            assertEquals(SearchContract.SearchState.Empty, awaitItem().state)
        }
    }

    @Test
    fun `search should debounce rapid inputs`() = runTest {
        // Given
        coEvery { connectionChecker.hasInternetConnection() } returns true
        every { getHomeSectionsUseCase.getHomeSearchSections(any()) } returns flowOf(HomeSectionsResponse())

        // When - Send rapid inputs
        viewModel.handleEvent(SearchContract.Event.OnSearch("t"))
        viewModel.handleEvent(SearchContract.Event.OnSearch("te"))
        viewModel.handleEvent(SearchContract.Event.OnSearch("tes"))
        viewModel.handleEvent(SearchContract.Event.OnSearch("test"))

        // Advance time by the debounce duration (assuming 500ms is your debounce time)
        advanceTimeBy(500)

        // Then - Should only make one API call for "test"
        coVerify(exactly = 1) { getHomeSectionsUseCase.getHomeSearchSections("test") }
        coVerify(exactly = 0) { getHomeSectionsUseCase.getHomeSearchSections(neq("test")) }
    }
}