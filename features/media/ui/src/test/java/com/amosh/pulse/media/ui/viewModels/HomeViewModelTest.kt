package com.amosh.pulse.media.ui.viewModels

import app.cash.turbine.test
import com.amosh.pulse.core.domain.ConnectionChecker
import com.amosh.pulse.core.domain.model.HomeSectionsResponse
import com.amosh.pulse.core.domain.model.UserData
import com.amosh.pulse.media.domain.useCase.GetHomeSectionsUseCase
import com.amosh.pulse.core.domain.useCases.GetUserDataUseCase
import com.amosh.pulse.media.ui.BaseTest
import com.amosh.pulse.media.ui.model.SectionsUiItem
import com.amosh.pulse.media.ui.mapper.SectionItemUiMapper
import com.amosh.pulse.media.ui.screens.homeScreen.HomeContract
import com.amosh.pulse.media.ui.screens.homeScreen.HomeViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalTime
class HomeViewModelTest : BaseTest() {
    private lateinit var viewModel: HomeViewModel
    private val getHomeSectionsUseCase = mockk<GetHomeSectionsUseCase>()
    private val getUserDataUseCase = mockk<GetUserDataUseCase>()
    private val mapper = mockk<SectionItemUiMapper>()
    private val connectionChecker = mockk<ConnectionChecker>()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Setup default happy path mocks
        coEvery { connectionChecker.hasInternetConnection() } returns true
        every { getUserDataUseCase.getUserData() } returns flowOf(UserData())
        every { getHomeSectionsUseCase.getHomeSections(any()) } returns flowOf(
            HomeSectionsResponse(
                sections = emptyList()
            )
        )
        every { mapper.fromList(any()) } returns emptyList()

        viewModel = HomeViewModel(
            getHomeSectionsUseCase,
            getUserDataUseCase,
            mapper,
            connectionChecker,
            testDispatcher
        )
    }

    @Test
    fun `init should load user data and sections`() = runTest {
        // Given
        val testUser = UserData(userName = "Test User")
        val testSections = listOf(SectionsUiItem(name = "Section 1"))
        val response = HomeSectionsResponse(sections = emptyList())

        coEvery { connectionChecker.hasInternetConnection() } returns true
        every { getUserDataUseCase.getUserData() } returns flowOf(testUser)
        every { getHomeSectionsUseCase.getHomeSections(1) } returns flowOf(response)
        every { mapper.fromList(any()) } returns testSections

        viewModel = HomeViewModel(
            getHomeSectionsUseCase,
            getUserDataUseCase,
            mapper,
            connectionChecker,
            testDispatcher
        )

        viewModel.userDataState.test {
            assertEquals(testUser, awaitItem())
            cancel()
        }

        viewModel.uiState.test {
            assertEquals(HomeContract.HomeState.Idle, awaitItem().state)
            assertEquals(HomeContract.HomeState.Empty, awaitItem().state)
            cancel()
        }
    }

    @Test
    fun `should show error when no internet`() = runTest {
        // Given
        coEvery { connectionChecker.hasInternetConnection() } returns false

        // When
        viewModel.handleEvent(HomeContract.Event.OnFetchHomeSections(false))

        // Then
        viewModel.uiState.test {
            assertEquals(HomeContract.HomeState.Idle, awaitItem().state)
            assertEquals(HomeContract.HomeState.NoInternet, awaitItem().state)
            cancel()
        }
    }
}