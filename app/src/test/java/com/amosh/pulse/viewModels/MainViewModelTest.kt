package com.amosh.pulse.viewModels

import app.cash.turbine.test
import com.amosh.pulse.BaseTest
import com.amosh.pulse.core.domain.model.UserData
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages
import com.amosh.pulse.core.domain.model.enums.SupportedTheme
import com.amosh.pulse.core.domain.useCases.AppLanguageUseCase
import com.amosh.pulse.core.domain.useCases.AppThemeUseCase
import com.amosh.pulse.core.domain.useCases.GetUserDataUseCase
import com.amosh.pulse.core.domain.useCases.UpdateUserDataUseCase
import com.amosh.pulse.ui.screens.MainViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
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
class MainViewModelTest : BaseTest() {
    private lateinit var viewModel: MainViewModel
    private val appLanguageUseCase = mockk<AppLanguageUseCase>()
    private val appThemeUseCase = mockk<AppThemeUseCase>()
    private val updateUserDataUseCase = mockk<UpdateUserDataUseCase>()
    private val getUserDataUseCase = mockk<GetUserDataUseCase>()
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Setup default happy path mocks
        coEvery { appLanguageUseCase.getAppLanguage() } returns flowOf(SupportedLanguages.EN)
        coEvery { appThemeUseCase.getAppTheme() } returns flowOf(SupportedTheme.LIGHT)
        coEvery { getUserDataUseCase.getUserData() } returns flowOf(UserData())

        viewModel = MainViewModel(
            appLanguageUseCase,
            appThemeUseCase,
            updateUserDataUseCase,
            getUserDataUseCase,
            testDispatcher
        )
    }

    @Test
    fun `init should load language, theme and user data`() = runTest {
        // Given
        val testLanguage = SupportedLanguages.AR
        val testTheme = SupportedTheme.DARK
        val testUser = UserData(userName = "Test User")

        coEvery { appLanguageUseCase.getAppLanguage() } returns flowOf(testLanguage)
        coEvery { appThemeUseCase.getAppTheme() } returns flowOf(testTheme)
        coEvery { getUserDataUseCase.getUserData() } returns flowOf(testUser)

        // Recreate ViewModel with new mocks
        viewModel = MainViewModel(
            appLanguageUseCase,
            appThemeUseCase,
            updateUserDataUseCase,
            getUserDataUseCase,
            testDispatcher
        )

        // Then verify the flows
        viewModel.currentLanguage.test {
            assertEquals(testLanguage, awaitItem())
            cancel()
        }

        viewModel.currentTheme.test {
            assertEquals(testTheme, awaitItem())
            cancel()
        }

        viewModel.userDataState.test {
            assertEquals(testUser, awaitItem())
            cancel()
        }
    }

    @Test
    fun `handleSetLanguage should update language`() = runTest {
        // Given
        val newLanguage = SupportedLanguages.EN
        coEvery { appLanguageUseCase.updateAppLanguage(newLanguage) } just Runs

        // When
        viewModel.handleSetLanguage(newLanguage)

        // Then
        coVerify(exactly = 1) { appLanguageUseCase.updateAppLanguage(newLanguage) }
    }

    @Test
    fun `handleSetTheme should update theme`() = runTest {
        // Given
        val newTheme = SupportedTheme.DARK
        coEvery { appThemeUseCase.updateAppTheme(newTheme) } just Runs

        // When
        viewModel.handleSetTheme(newTheme)

        // Then
        coVerify(exactly = 1) { appThemeUseCase.updateAppTheme(newTheme) }
    }

    @Test
    fun `updateUserData should update name and profile pic`() = runTest {
        // Given
        val name = "New Name"
        val pic = "pic_url"
        coEvery { updateUserDataUseCase.updateName(name) } just Runs
        coEvery { updateUserDataUseCase.updateProfilePic(pic) } just Runs

        // When
        viewModel.updateUserData(name, pic)

        // Then
        coVerify(exactly = 1) { updateUserDataUseCase.updateName(name) }
        coVerify(exactly = 1) { updateUserDataUseCase.updateProfilePic(pic) }
    }
}