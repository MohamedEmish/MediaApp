package com.amosh.pulse.core.domain.useCase

import app.cash.turbine.test
import com.amosh.pulse.core.domain.BaseTest
import com.amosh.pulse.core.domain.Repository
import com.amosh.pulse.core.domain.model.UserData
import com.amosh.pulse.core.domain.useCases.GetUserDataUseCase
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
class GetUserDataUseCaseTest : BaseTest() {
    private val repository = mockk<Repository>()
    private val useCase = GetUserDataUseCase(repository)

    @Test
    fun `getUserData should return flow from repository`() = runTest {
        // Given
        val expectedUserData = UserData(userName = "Test User")
        every { repository.getUserData() } returns flowOf(expectedUserData)

        // When & Then
        useCase.getUserData().test {
            assertEquals(expectedUserData, awaitItem())
            awaitComplete()
        }
    }
}