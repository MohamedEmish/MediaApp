package com.amosh.pulse.core.domain.useCase

import app.cash.turbine.test
import com.amosh.pulse.core.domain.BaseTest
import com.amosh.pulse.core.domain.Repository
import com.amosh.pulse.core.domain.model.enums.SupportedLanguages
import com.amosh.pulse.core.domain.useCases.AppLanguageUseCase
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
class AppLanguageUseCaseTest : BaseTest() {
    private val repository = mockk<Repository>()
    private val useCase = AppLanguageUseCase(repository)

    @Test
    fun `getAppLanguage should return flow from repository`() = runTest {
        // Given
        val expectedLanguage = SupportedLanguages.EN
        every { repository.getAppLanguage() } returns flowOf(expectedLanguage)

        // When & Then
        useCase.getAppLanguage().test {
            assertEquals(expectedLanguage, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `updateAppLanguage should call repository`() = runTest {
        // Given
        val language = SupportedLanguages.AR
        coEvery { repository.updateAppLanguage(language) } returns Unit

        // When
        useCase.updateAppLanguage(language)

        // Then
        coVerify { repository.updateAppLanguage(language) }
    }
}