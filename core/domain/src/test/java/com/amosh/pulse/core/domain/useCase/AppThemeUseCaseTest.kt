package com.amosh.pulse.core.domain.useCase

import app.cash.turbine.test
import com.amosh.pulse.core.domain.BaseTest
import com.amosh.pulse.core.domain.Repository
import com.amosh.pulse.core.domain.model.enums.SupportedTheme
import com.amosh.pulse.core.domain.useCases.AppThemeUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalTime
class AppThemeUseCaseTest : BaseTest() {
    private val repository = mockk<Repository>()
    private val useCase = AppThemeUseCase(repository)

    @Test
    fun `getAppTheme should return flow from repository`() = runTest {
        // Given
        val expectedTheme = SupportedTheme.DARK
        every { repository.getAppTheme() } returns flowOf(expectedTheme)

        // When & Then
        useCase.getAppTheme().test {
            assertEquals(expectedTheme, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `updateAppTheme should call repository`() = runTest {
        // Given
        val theme = SupportedTheme.LIGHT
        coEvery { repository.updateAppTheme(theme) } returns Unit

        // When
        useCase.updateAppTheme(theme)

        // Then
        coVerify { repository.updateAppTheme(theme) }
    }
}