package com.amosh.pulse.core.domain.useCase

import app.cash.turbine.test
import com.amosh.pulse.core.domain.BaseTest
import com.amosh.pulse.core.domain.Repository
import com.amosh.pulse.core.domain.model.HomeSectionsResponse
import com.amosh.pulse.core.domain.useCases.GetHomeSectionsUseCase
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
class GetHomeSectionsUseCaseTest : BaseTest() {
    private val repository = mockk<Repository>()
    private val useCase = GetHomeSectionsUseCase(repository)

    @Test
    fun `getHomeSections should return flow from repository`() = runTest {
        // Given
        val page = 1
        val expectedResponse = HomeSectionsResponse(sections = emptyList())
        every { repository.getHomeSections(page) } returns flowOf(expectedResponse)

        // When & Then
        useCase.getHomeSections(page).test {
            assertEquals(expectedResponse, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `getHomeSearchSections should return flow from repository`() = runTest {
        // Given
        val query = "test"
        val expectedResponse = HomeSectionsResponse(sections = emptyList())
        every { repository.getHomeSearchSections(query) } returns flowOf(expectedResponse)

        // When & Then
        useCase.getHomeSearchSections(query).test {
            assertEquals(expectedResponse, awaitItem())
            awaitComplete()
        }
    }
}